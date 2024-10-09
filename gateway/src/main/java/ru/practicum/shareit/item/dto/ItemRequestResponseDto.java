package ru.practicum.shareit.item.dto;

import lombok.Data;

/**
 * DTO (Data Transfer Object) для ответа на запрос информации о предмете.
 * <p>
 * Этот класс используется для передачи данных о предмете, включая его идентификатор,
 * название и идентификатор владельца.
 * </p>
 */
@Data
public class ItemRequestResponseDto {

    private Long itemId;

    private String name;

    private Long ownerId;
}
