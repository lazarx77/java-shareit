package ru.practicum.shareit.exception;

/**
 * Исключение InternalServerException представляет собой пользовательское исключение,
 * которое выбрасывается в случае возникновения непредвиденной ошибки на стороне сервера.
 */
public class InternalServerException extends RuntimeException {
    public InternalServerException(String message) {
        super(message);
    }
}
