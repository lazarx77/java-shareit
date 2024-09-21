package ru.practicum.shareit.exception;

/**
 * Исключение, представляющее собой ситуацию, когда ресурс недоступен.
 * <p>
 * Это исключение может быть выброшено в тех случаях, когда запрашиваемый ресурс
 * (например, предмет для бронирования) недоступен для использования или бронирования.
 */
public class NotAvailableException extends RuntimeException {
    public NotAvailableException(String message) {
        super(message);
    }
}
