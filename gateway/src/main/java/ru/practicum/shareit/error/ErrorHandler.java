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
     * , {@link ValidationException}.
     *
     * @param e исключение, связанное с ошибкой валидации
     * @return карта с сообщением об ошибке и описанием
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(final Exception e) {
        log.error("Ошибка валидации данных: {}.", e.getMessage());
        return Map.of(
                "error", "Ошибка валидации данных",
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
}
