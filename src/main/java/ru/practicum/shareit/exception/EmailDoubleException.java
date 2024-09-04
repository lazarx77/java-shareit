package ru.practicum.shareit.exception;

/**
 * Исключение EmailDoubleException представляет собой пользовательское исключение,
 * которое выбрасывается в случае попытки регистрации или использования адреса электронной почты,
 * который уже существует в системе. Это исключение наследуется от класса {@link RuntimeException},
 * что позволяет его использовать в контексте выполнения программы без необходимости
 * обработки проверяемых исключений.
 *
 * <p>Конструктор принимает сообщение, которое описывает причину возникновения исключения.</p>
 *
 * <p>Пример использования:</p>
 * <pre>
 * if (emailExists(email)) {
 *     throw new EmailDoubleException("Адрес электронной почты уже зарегистрирован.");
 * }
 * </pre>
 *
 * Метод {@code getMessage()} переопределен для получения сообщения об ошибке,
 * которое передается в конструктор родительского класса.
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
