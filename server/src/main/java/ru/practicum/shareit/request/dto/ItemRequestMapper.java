package ru.practicum.shareit.request.dto;

import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.ItemRequestResponseMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Класс ItemRequestMapper предназначен для преобразования объектов между
 * различными представлениями (DTO) и сущностями (Entity) запросов на предметы.
 * Содержит методы для маппинга данных из NewItemRequestDto в ItemRequest
 * и из ItemRequest в ItemRequestDto.
 */
public class ItemRequestMapper {

    /**
     * Преобразует NewItemRequestDto в ItemRequest.
     *
     * @param dto объект NewItemRequestDto, содержащий данные нового запроса
     * @return созданный объект ItemRequest с установленными значениями
     */
    public static ItemRequest mapToItemRequest(NewItemRequestDto dto) {
        LocalDateTime now = LocalDateTime.now();
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(now);
        itemRequest.setDescription(dto.getDescription());
        return itemRequest;
    }

    /**
     * Преобразует ItemRequest в ItemRequestDto.
     *
     * @param itemRequest объект ItemRequest, который необходимо преобразовать
     * @param itemService сервис для получения связанных предметов
     * @return объект ItemRequestDto с данными из ItemRequest и списком предметов
     */
    public static ItemRequestDto mapToItemRequestDto(ItemRequest itemRequest, ItemService itemService) {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(itemRequest.getId());
        itemRequestDto.setCreated(itemRequest.getCreated());
        itemRequestDto.setDescription(itemRequest.getDescription());
        List<Item> items = itemService.getItemsByRequestId(itemRequest.getId());

        if (items != null && !items.isEmpty()) {
            itemRequestDto.setItems(items.stream().map(ItemRequestResponseMapper::mapToItemRequestResponse).toList());
        } else {
            itemRequestDto.setItems(Collections.emptyList());
        }
        return itemRequestDto;
    }
}
