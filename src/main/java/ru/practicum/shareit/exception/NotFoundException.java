package ru.practicum.shareit.exception;

/**
 * Исключение NotFoundException представляет собой пользовательское исключение,
 * которое выбрасывается в случае, если запрашиваемый ресурс не найден в системе.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
