package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.exception.EmailDoubleException;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

/**
 * UserValidator предоставляет методы для валидации данных пользователей.
 * Он включает проверку на уникальность электронной почты.
 */
@Slf4j
public class UserValidator {

    /**
     * Проверяет уникальность электронной почты пользователя.
     *
     * @param user Optional пользователя для проверки на дублирование электронной почты.
     * @throws EmailDoubleException если электронная почта уже используется другим пользователем.
     */
    public static void checkEmailIsUnique(Optional<User> user) {
        if (user.isPresent()) {
            throw new EmailDoubleException("Email должен быть уникальным");
        }
    }
}
