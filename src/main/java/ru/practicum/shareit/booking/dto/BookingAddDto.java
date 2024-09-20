package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@NoArgsConstructor
@Data
public class BookingAddDto {
    Long id;

    @NotNull(message = "Время начала бронирования не может быть null")
    private LocalDateTime start;

    @NotNull(message = "Время окончания бронирования не может быть null")
    private LocalDateTime end;

    @NotNull(message = "Бронируемую вещь необходимо указать")
    private Long itemId;

    private User booker;

    private Status status;

    private State state;
}