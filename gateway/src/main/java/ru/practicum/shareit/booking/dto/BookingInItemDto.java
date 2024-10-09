package ru.practicum.shareit.booking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) для представления информации о бронировании предмета.
 * Используется для передачи данных о бронировании между слоями приложения.
 * Он содержит идентификатор бронирования, информацию о времени начала и окончания бронирования, а также о
 * бронируемом предмете и пользователе, который делает бронирование, статус бронирования и
 * состояние бронивания.
 */
@NoArgsConstructor
@Data
public class BookingInItemDto {

    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Long bookerId;
    private Status status;
    private BookingState state;
}
