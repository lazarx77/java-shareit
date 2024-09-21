package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) для представления информации о бронировании.
 * <p>
 * Этот класс используется для передачи данных о бронировании между слоями приложения.
 * Он содержит информацию о времени начала и окончания бронирования, а также о
 * бронируемом предмете и пользователе, который делает бронирование.
 */
@NoArgsConstructor
@Data
public class BookingDto {

    Long id;

    @NotNull(message = "Время начала бронирования не может быть null")
    private LocalDateTime start;

    @NotNull(message = "Время окончания бронирования не может быть null")
    private LocalDateTime end;

    private Item item;

    private User booker;

    private Status status;

    private State state;
}
