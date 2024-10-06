package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestMapper;
import ru.practicum.shareit.request.dto.NewItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@AllArgsConstructor
@Slf4j
public class ItemRequestController {

    private final ItemRequestService itemRequestService;
    private final ItemService itemService;

    @PostMapping
    public ItemRequest add(@RequestHeader("X-Sharer-User-Id") Long requestorId,
                           @RequestBody NewItemRequestDto dto) {
        return itemRequestService.addNewRequest(requestorId, ItemRequestMapper.mapToItemRequest(dto));
    }

    @GetMapping
    public List<ItemRequestDto> getRequestsOfRequestor(@RequestHeader("X-Sharer-User-Id") Long requestorId) {
        return itemRequestService.getRequestsOfRequestor(requestorId)
                .stream()
                .map(itemRequest -> ItemRequestMapper.mapToItemRequestDto(itemRequest, itemService))
                .toList();
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAllRequestsOfOtherUsers(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestService.getAllRequestsOfOtherUsers(userId)
                .stream()
                .map(itemRequest -> ItemRequestMapper.mapToItemRequestDto(itemRequest, itemService))
                .toList();
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getRequestById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @PathVariable("requestId") Long requestId) {
        ItemRequestDto dto = ItemRequestMapper.mapToItemRequestDto(itemRequestService.getRequestById(userId, requestId), itemService);
        log.info(dto.toString());
        return dto;
    }
}
