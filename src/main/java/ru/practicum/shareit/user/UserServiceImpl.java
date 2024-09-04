package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.List;


/**
 * Класс UserServiceImpl реализует интерфейс UserService и предоставляет
 * функциональность для работы с пользователями, включая операции
 * создания, обновления, получения и удаления пользователей.
 *
 * <p>Класс использует UserRepository для взаимодействия с хранилищем данных
 * и UserMapper для преобразования объектов User в UserDto и обратно.</p>
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserDto> getAll() {
        return userRepository.getAll().stream().map(UserMapper::mapToDto).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User createUser(User user) {
        UserValidatorService.validateEmailDouble(user, userRepository.getAll().stream().toList());
        log.info("Создание пользователя: {} ", user.getName());
        return userRepository.createUser(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User update(Long id, User updatedUser) {
        UserValidatorService.validateId(id);
        log.info("Обновление пользователя с id: {} ", id);
        return userRepository.findById(id)
                .map(existingUser -> {
                    UserValidatorService.validateEmailDouble(updatedUser, userRepository.getAll().stream().toList());
                    updatedUser.setId(id);
                    return userRepository.update(updatedUser);
                })
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id = " + id + " не существует"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDto findById(Long id) {
        return UserMapper.mapToDto(userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id = " + id + " не существует")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }
}
