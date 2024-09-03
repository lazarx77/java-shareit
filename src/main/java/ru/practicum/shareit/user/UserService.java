package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> getAll();

    User createUser(User user);

    User update(User updatedUser);

    UserDto findById(Long id);
}
