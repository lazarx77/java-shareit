package ru.practicum.shareit.user.dto;

import ru.practicum.shareit.user.model.User;

/**
 * Класс UserMapper предоставляет статические методы для преобразования объектов
 * между различными представлениями данных, такими как {@link User} и {@link UserDto}.
 * Этот класс служит для упрощения процесса маппинга данных, позволяя избежать дублирования
 * кода и обеспечивая чистоту архитектуры приложения.
 */
public class UserMapper {

    /**
     * Преобразует объект типа {@link User} в объект типа {@link UserDto}.
     *
     * @param user объект типа {@link User}, который необходимо преобразовать.
     * @return объект типа {@link UserDto}, содержащий данные из переданного объекта {@link User}.
     */
    public static UserDto mapToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    /**
     * Преобразует объект типа {@link UserDto} в объект типа {@link User}.
     *
     * @param dto объект типа {@link UserDto}, который необходимо преобразовать.
     * @return объект типа {@link User}, содержащий данные из переданного объекта {@link UserDto}.
     */
    public static User mapToUser(UserDto dto) {
        return new User(
                dto.getId(),
                dto.getName(),
                dto.getEmail()
        );
    }
}
