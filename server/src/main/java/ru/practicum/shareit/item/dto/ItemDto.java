package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingInItemDto;

import java.util.List;

/**
 * DTO (Data Transfer Object) для представления предмета.
 * <p>
 * Этот класс используется для передачи данных о предметах, включая
 * идентификатор, название, описание, статус доступности, информацию о
 * последнем и следующем бронировании, а также список комментариев.
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class ItemDto {

    private Long id;

    @NotBlank(message = "Название не может быть пустым")
    private String name;

    @NotBlank(message = "Описание не может быть пустым")
    private String description;

    @NotNull(message = "Статус доступности не может быть пустым")
    private Boolean available;

    private BookingInItemDto lastBooking;

    private BookingInItemDto nextBooking;

    private List<CommentDto> comments;

    private Long requestId;

    public Boolean isAvailable() {
        return available;
    }
}
