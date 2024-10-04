package ru.practicum.shareit.item.dto;

import lombok.Data;

@Data
public class ItemRequestResponseDto {

    Long itemId;

    String name;

    Long ownerId;
}
