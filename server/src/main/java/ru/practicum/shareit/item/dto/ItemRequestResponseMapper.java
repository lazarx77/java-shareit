package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;

/**
 * Класс ItemRequestResponseMapper предназначен для преобразования объектов
 * типа Item в объекты типа ItemRequestResponseDto.
 * Содержит методы для маппинга данных из сущности предмета в DTO для ответа на запрос.
 */
public class ItemRequestResponseMapper {

    /**
     * Преобразует объект Item в ItemRequestResponseDto.
     *
     * @param item объект Item, который необходимо преобразовать
     * @return созданный объект ItemRequestResponseDto с данными из Item
     */
    public static ItemRequestResponseDto mapToItemRequestResponse(Item item) {
        ItemRequestResponseDto itemRequestResponseDto = new ItemRequestResponseDto();
        itemRequestResponseDto.setItemId(item.getId());
        itemRequestResponseDto.setOwnerId(item.getOwner().getId());
        itemRequestResponseDto.setName(item.getName());
        return itemRequestResponseDto;
    }
}
