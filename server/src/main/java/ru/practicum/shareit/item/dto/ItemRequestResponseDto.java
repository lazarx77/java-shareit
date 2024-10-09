package ru.practicum.shareit.item.dto;

import lombok.Data;

/**
 * DTO (Data Transfer Object) для представления ответа на запрос предмета.
 * Содержит информацию о предмете, включая его идентификатор, имя и идентификатор владельца.
 */
@Data
public class ItemRequestResponseDto {

    private Long itemId;

    private String name;

    private Long ownerId;
}
