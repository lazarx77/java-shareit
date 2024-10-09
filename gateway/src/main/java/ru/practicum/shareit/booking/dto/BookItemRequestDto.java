package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) для запроса на бронирование предмета.
 * <p>
 * Этот класс используется для передачи данных о запросе на бронирование,
 * включая время начала и окончания бронирования, а также идентификатор
 * бронируемого предмета.
 * </p>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookItemRequestDto {

    @NotNull(message = "Время начала бронирования не может быть null")
    private LocalDateTime start;

    @NotNull(message = "Время окончания бронирования не может быть null")
    private LocalDateTime end;

    @NotNull(message = "Бронируемую вещь необходимо указать")
    private long itemId;
}
