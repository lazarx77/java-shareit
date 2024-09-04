package ru.practicum.shareit.exception;

/**
 * Исключение ValidationException представляет собой пользовательское исключение,
 * которое выбрасывается в случае, если данные не проходят валидацию и не соответствуют
 * установленным требованиям. Это исключение наследуется от класса {@link RuntimeException},
 * что позволяет его использовать в контексте выполнения программы без необходимости
 * обработки проверяемых исключений.
 *
 * <p>Конструктор принимает сообщение, которое описывает причину возникновения исключения,
 * и передает его в конструктор родительского класса {@link RuntimeException}.</p>
 *
 * <p>Метод {@code getMessage()} переопределен для получения сообщения об ошибке,
 * которое передается в конструктор родительского класса.</p>
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
