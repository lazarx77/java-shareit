package ru.practicum.shareit.request.dto;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.ItemRequestResponseMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class ItemRequestMapper {

    public static ItemRequest mapToItemRequest(NewItemRequestDto dto) {
        LocalDateTime now = LocalDateTime.now();
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(now);
        itemRequest.setDescription(dto.getDescription());
        return itemRequest;
    }

    public static ItemRequestDto mapToItemRequestDto(ItemRequest itemRequest, ItemService itemService) {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(itemRequest.getId());
        itemRequestDto.setCreated(itemRequest.getCreated());
        itemRequestDto.setDescription(itemRequest.getDescription());
        List<Item> items = itemService.getItemsByRequestId(itemRequest.getId());
        if(!items.isEmpty()) {
            itemRequestDto.setItems(items.stream().map(ItemRequestResponseMapper::mapToItemRequestResponse).toList());
        }
        return itemRequestDto;
    }
}
