package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.exception.EmailDoubleException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.model.User;

import java.util.List;

/**
 * Сервис UserValidatorService предоставляет методы для валидации данных пользователей.
 * Он включает проверки на наличие идентификатора пользователя и уникальность электронной почты.
 */
@Slf4j
public class UserValidatorService {

    /**
     * Проверяет наличие идентификатора пользователя.
     *
     * @param id Уникальный идентификатор пользователя.
     * @throws ValidationException если идентификатор равен null.
     */
    public static void validateId(Long id) {
        log.info("Проверка наличия id пользователя: {} ", id);
        if (id == null) {
            throw new ValidationException("Id должен быть указан");
        }
    }

    /**
     * Проверяет уникальность электронной почты пользователя.
     *
     * @param sourceUser Объект пользователя, чья электронная почта проверяется.
     * @param users      Список всех пользователей для проверки на дублирование электронной почты.
     * @throws EmailDoubleException если электронная почта уже используется другим пользователем.
     */
    public static void validateEmailDouble(User sourceUser, List<User> users) {
        for (User currentUser : users) {
            if (currentUser.getEmail().equals(sourceUser.getEmail())) {
                throw new EmailDoubleException("Email должен быть уникальным");
            }
        }
    }
}
