package ru.practicum.shareit.exception;

/**
 * Исключение NotFoundException представляет собой пользовательское исключение,
 * которое выбрасывается в случае, если запрашиваемый ресурс не найден в системе.
 * Это исключение наследуется от класса {@link RuntimeException}, что позволяет его использовать
 * в контексте выполнения программы без необходимости обработки проверяемых исключений.
 *
 * <p>Конструктор принимает сообщение, которое описывает причину возникновения исключения,
 * и передает его в конструктор родительского класса {@link RuntimeException}.</p>
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
