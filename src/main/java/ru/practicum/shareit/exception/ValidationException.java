package ru.practicum.shareit.exception;

/**
 * Исключение ValidationException представляет собой пользовательское исключение,
 * которое выбрасывается в случае, если данные не проходят валидацию и не соответствуют
 * установленным требованиям.
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
