package ru.practicum.shareit.error;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.EmailDoubleException;
import ru.practicum.shareit.exception.ItemDoNotBelongToUser;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ErrorHandlerTest {

    private ErrorHandler errorHandler;

    @BeforeEach
    void setUp() {
        errorHandler = new ErrorHandler();
    }

    @Test
    void handleValidationException_ShouldReturnBadRequest() {
        String message = "Validation failed";
        ValidationException exception = Mockito.mock(ValidationException.class);
        Mockito.when(exception.getMessage()).thenReturn(message);

        Map<String, String> response = errorHandler.handleValidationException(exception);

        assertEquals("Ошибка валидации данных", response.get("error"));
        assertEquals(message, response.get("description"));
    }

    @Test
    void handleNotFoundException_ShouldReturnNotFound() {
        String message = "Resource not found";
        NotFoundException exception = Mockito.mock(NotFoundException.class);
        Mockito.when(exception.getMessage()).thenReturn(message);

        Map<String, String> response = errorHandler.handleNotFoundException(exception);

        assertEquals("Ошибка с входными параметрами.", response.get("error"));
        assertEquals(message, response.get("description"));
    }

    @Test
    void handleThrowable_ShouldReturnInternalServerError() {
        String message = "An unexpected error occurred";
        Throwable exception = Mockito.mock(Throwable.class);
        Mockito.when(exception.getMessage()).thenReturn(message);

        Map<String, String> response = errorHandler.handleThrowable(exception);

        assertEquals("Возникла ошибка.", response.get("error"));
        assertEquals(message, response.get("description"));
    }

    @Test
    void handleEmailDoubleException_ShouldReturnConflict() {
        String message = "Email already exists";
        EmailDoubleException exception = Mockito.mock(EmailDoubleException.class);
        Mockito.when(exception.getMessage()).thenReturn(message);

        Map<String, String> response = errorHandler.handleEmailDoubleException(exception);

        assertEquals("Повтор email", response.get("error"));
        assertEquals(message, response.get("description"));
    }

    @Test
    void handleItemDoNotBelongToUser_ShouldReturnForbidden() {
        String message = "Item does not belong to user";
        ItemDoNotBelongToUser exception = Mockito.mock(ItemDoNotBelongToUser.class);
        Mockito.when(exception.getMessage()).thenReturn(message);

        Map<String, String> response = errorHandler.handleItemDoNotBelongToUser(exception);

        assertEquals("Ошибка - для текущего пользователя в доступе отказано", response.get("error"));
        assertEquals(message, response.get("description"));
    }
}
