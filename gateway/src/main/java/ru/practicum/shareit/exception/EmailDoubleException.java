package ru.practicum.shareit.exception;

/**
 * Исключение EmailDoubleException представляет собой пользовательское исключение,
 * которое выбрасывается в случае попытки регистрации или использования адреса электронной почты,
 * который уже существует в системе.
 */
public class EmailDoubleException extends RuntimeException {
    public EmailDoubleException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
