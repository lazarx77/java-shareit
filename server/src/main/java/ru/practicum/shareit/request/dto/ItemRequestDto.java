package ru.practicum.shareit.request.dto;

import lombok.Data;
import ru.practicum.shareit.item.dto.ItemRequestResponseDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO (Data Transfer Object) для представления запроса на предмет.
 * Содержит информацию о запросе, включая его идентификатор, описание,
 * дату создания и список связанных предметов.
 */
@Data
public class ItemRequestDto {

    private Long id;
    private String description;
    private LocalDateTime created;
    private List<ItemRequestResponseDto> items;
}
