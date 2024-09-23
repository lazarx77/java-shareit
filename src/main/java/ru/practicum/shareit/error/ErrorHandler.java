package ru.practicum.shareit.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exception.*;

import java.util.Map;

/**
 * Класс {@code ErrorHandler} обрабатывает исключения, возникающие в приложении.
 *
 * <p>Этот класс использует аннотацию {@link RestControllerAdvice}, чтобы перехватывать
 * исключения, выбрасываемые контроллерами, и возвращать соответствующие ответы с
 * информацией об ошибках.</p>
 *
 * <p>Каждый метод в этом классе обрабатывает определенный тип исключения и возвращает
 * ответ с кодом состояния HTTP и сообщением об ошибке.</p>
 */
@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    /**
     * Обрабатывает исключения валидации, такие как {@link MethodArgumentNotValidException}
     * , {@link ValidationException} и {@link NotAvailableException}.
     *
     * @param e исключение, связанное с ошибкой валидации
     * @return карта с сообщением об ошибке и описанием
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, ValidationException.class, NotAvailableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(final Exception e) {
        log.error("Ошибка валидации данных: {}.", e.getMessage());
        return Map.of(
                "error", "Ошибка валидации данных",
                "description", e.getMessage()
        );
    }

    /**
     * Обрабатывает исключение {@link NotFoundException}, которое возникает, когда
     * запрашиваемый ресурс не найден.
     *
     * @param e исключение, связанное с отсутствием ресурса
     * @return карта с сообщением об ошибке и описанием
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundException(final NotFoundException e) {
        log.error("Ошибка с входными параметрами: {}.", e.getMessage());
        return Map.of(
                "error", "Ошибка с входными параметрами.",
                "description", e.getMessage()
        );
    }

    /**
     * Обрабатывает любые другие исключения, которые не были перехвачены
     * другими методами.
     *
     * @param e общее исключение
     * @return карта с сообщением об ошибке и описанием
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleThrowable(final Throwable e) {
        log.error("Возникла ошибка: {}.", e.getMessage());
        return Map.of(
                "error", "Возникла ошибка.",
                "description", e.getMessage()
        );
    }

    /**
     * Обрабатывает исключение {@link EmailDoubleException}, которое возникает,
     * когда пользователь пытается зарегистрироваться с уже существующим адресом электронной почты.
     *
     * @param e исключение, связанное с дублированием email
     * @return карта с сообщением об ошибке и описанием
     */
    @ExceptionHandler(EmailDoubleException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleEmailDoubleException(final EmailDoubleException e) {
        log.error("Ошибка с входными параметрами(повтор email): {}.", e.getMessage());
        return Map.of(
                "error", "Повтор email",
                "description", e.getMessage()
        );
    }

    /**
     * Обрабатывает исключение {@link ItemDoNotBelongToUser}, которое возникает,
     * когда пользователь пытается обновить предмет, который ему не принадлежит.
     *
     * @param e исключение, связанное с попыткой обновления чужого предмета
     * @return карта с сообщением об ошибке и описанием
     */
    @ExceptionHandler(ItemDoNotBelongToUser.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> handleItemDoNotBelongToUser(final ItemDoNotBelongToUser e) {
        log.error("Ошибка - для текущего пользователя в доступе отказано: {}.", e.getMessage());
        return Map.of(
                "error", "Ошибка - для текущего пользователя в доступе отказано",
                "description", e.getMessage()
        );
    }
}
