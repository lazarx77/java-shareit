package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingInItemDto;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Класс ItemOwnerDto представляет собой Data Transfer Object (DTO) для передачи данных о предмете
 * с точки зрения его владельца. Этот класс используется для обмена информацией между слоями приложения,
 * например, между контроллерами и сервисами, и содержит только те поля, которые необходимы для
 * отображения информации о предмете владельцу.
 *
 * <p>Поля класса:</p>
 * <ul>
 *     <li><b>name</b> - название предмета;</li>
 *     <li><b>description</b> - описание предмета;</li>
 * </ul>
 */
@NoArgsConstructor
@Data
public class ItemOwnerDto {
    private String name;
    private String description;
    private BookingInItemDto lastBooking;
    private BookingInItemDto futureBooking;
}
