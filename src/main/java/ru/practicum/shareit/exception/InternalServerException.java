package ru.practicum.shareit.exception;

/**
 * Исключение InternalServerException представляет собой пользовательское исключение,
 * которое выбрасывается в случае возникновения непредвиденной ошибки на стороне сервера.
 * Это исключение наследуется от класса {@link RuntimeException}, что позволяет его использовать
 * в контексте выполнения программы без необходимости обработки проверяемых исключений.
 *
 * <p>Конструктор принимает сообщение, которое описывает причину возникновения исключения,
 * и передает его в конструктор родительского класса {@link RuntimeException}.</p>
 */
public class InternalServerException extends RuntimeException {
    public InternalServerException(String message) {
        super(message);
    }
}
