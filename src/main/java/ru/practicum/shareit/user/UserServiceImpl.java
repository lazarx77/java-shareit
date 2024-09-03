package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAll() {
        return userRepository.getAll().stream().map(UserMapper::mapToDto).toList();
    }

    @Override
    public User createUser(User user){
        return userRepository.createUser(user);
    }


    @Override
    public User update(User updatedUser){
        return userRepository.update(updatedUser);
    }

    @Override
    public UserDto findById(Long id){
        return UserMapper.mapToDto(userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id = " + id + " не существует")));
    }
}
