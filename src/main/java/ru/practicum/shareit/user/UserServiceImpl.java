package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
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
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User addUser(User user) {
        UserValidatorService.checkEmailIsUnique(userRepository.findUserByEmail(user.getEmail()));
        log.info("Создание пользователя: {} ", user.getName());
        return userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User updateUser(Long id, User updatedUser) {
        UserValidatorService.validateId(id);
        log.info("Обновление пользователя с id: {} ", id);
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id = "
                        + updatedUser.getId() + " не существует"));
        updatedUser.setId(id);
        if (updatedUser.getEmail() != null) {
            UserValidatorService.checkEmailIsUnique(userRepository.findUserByEmail(updatedUser.getEmail()));
        } else {
            updatedUser.setEmail(user.getEmail());
        }
        if (updatedUser.getName() == null) {
            updatedUser.setName(user.getName());
        }
        return userRepository.save(updatedUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id = " + id + " не существует"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
