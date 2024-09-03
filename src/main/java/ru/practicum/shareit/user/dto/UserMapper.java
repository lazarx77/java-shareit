package ru.practicum.shareit.user.dto;

import ru.practicum.shareit.user.model.User;

public class UserMapper {

    public static UserDto mapToDto(User user) {
        return new UserDto(
                user.getName(),
                user.getEmail()
        );
    }
}
