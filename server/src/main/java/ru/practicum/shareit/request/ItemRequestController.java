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
 * Контроллер для управления запросами на предметы.
 * Обрабатывает HTTP-запросы, связанные с созданием и получением запросов на предметы.
 */
@RestController
@RequestMapping(path = "/requests")
@AllArgsConstructor
@Slf4j
public class ItemRequestController {

    private final ItemRequestService itemRequestService;
    private final ItemService itemService;

    /**
     * Создает новый запрос на предмет.
     *
     * @param requestorId идентификатор пользователя, создающего запрос
     * @param dto         объект NewItemRequestDto, содержащий данные нового запроса
     * @return созданный объект ItemRequest
     */
    @PostMapping
    public ItemRequest add(@RequestHeader("X-Sharer-User-Id") Long requestorId, @RequestBody NewItemRequestDto dto) {
        return itemRequestService.addNewRequest(requestorId, ItemRequestMapper.mapToItemRequest(dto));
    }

    /**
     * Получает все запросы текущего пользователя.
     *
     * @param requestorId идентификатор пользователя
     * @return список запросов текущего пользователя в виде ItemRequestDto
     */
    @GetMapping
    public List<ItemRequestDto> getRequestsOfRequestor(@RequestHeader("X-Sharer-User-Id") Long requestorId) {
        return itemRequestService.getRequestsOfRequestor(requestorId).stream().map(itemRequest ->
                ItemRequestMapper.mapToItemRequestDto(itemRequest, itemService)).toList();
    }

    /**
     * Получает все запросы на предметы от других пользователей.
     *
     * @param userId идентификатор пользователя
     * @return список запросов от других пользователей в виде ItemRequestDto
     */
    @GetMapping("/all")
    public List<ItemRequestDto> getAllRequestsOfOtherUsers(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestService.getAllRequestsOfOtherUsers(userId).stream().map(itemRequest ->
                ItemRequestMapper.mapToItemRequestDto(itemRequest, itemService)).toList();
    }

    /**
     * Получает запрос на предмет по его идентификатору.
     *
     * @param userId    идентификатор пользователя
     * @param requestId идентификатор запроса
     * @return объект ItemRequestDto, представляющий запрашиваемый запрос
     */
    @GetMapping("/{requestId}")
    public ItemRequestDto getRequestById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @PathVariable("requestId") Long requestId) {
        ItemRequestDto dto = ItemRequestMapper
                .mapToItemRequestDto(itemRequestService.getRequestById(userId, requestId), itemService);
        log.info(dto.toString());
        return dto;
    }
}
