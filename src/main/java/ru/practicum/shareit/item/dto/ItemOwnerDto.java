package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingInItemDto;

import java.util.List;

/**
 * DTO (Data Transfer Object) для представления предмета с информацией о владельце.
 * <p>
 * Этот класс используется для передачи данных о предметах, включая
 * название, описание, информацию о последнем и следующем бронировании,
 * а также список комментариев.
 */
@NoArgsConstructor
@Data
public class ItemOwnerDto {
    private String name;
    private String description;
    private BookingInItemDto lastBooking;
    private BookingInItemDto nextBooking;
    private List<CommentDto> comments;
}
