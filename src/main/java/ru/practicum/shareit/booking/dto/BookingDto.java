package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
//@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDto {
    long id;

    @NotNull(message = "Название не может быть null")
    @NotBlank(message = "Название не может быть пустым")
    private LocalDateTime startDate;

    @NotNull(message = "Название не может быть null")
    @NotBlank(message = "Название не может быть пустым")
    private LocalDateTime endDate;

    @NotNull(message = "Бронируемую вещь необходимо указать")
    private Item item;

    private User booker;

    private Status status;

    private State state;
}
