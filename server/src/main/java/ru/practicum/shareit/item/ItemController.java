package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

/**
 * Контроллер для управления предметами в системе.
 * Обрабатывает HTTP-запросы, связанные с предметами, и взаимодействует с сервисами для выполнения операций.
 */
@RestController
@RequestMapping("/items")
@AllArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final BookingService bookingService;


    /**
     * Добавляет новый предмет.
     *
     * @param userId уникальный идентификатор пользователя, добавляющего предмет.
     * @param dto    объект, содержащий данные о предмете.
     * @return добавленный предмет.
     */
    @PostMapping
    public Item add(@RequestHeader("X-Sharer-User-Id") Long userId,
                    @Validated @RequestBody ItemDto dto) {
        return itemService.addNewItem(userId, dto);
    }

    /**
     * Обновляет информацию о существующем предмете.
     *
     * @param userId уникальный идентификатор пользователя, обновляющего предмет.
     * @param id     уникальный идентификатор предмета, который необходимо обновить.
     * @param dto    объект, содержащий обновленные данные о предмете.
     * @return обновленный предмет.
     */
    @PatchMapping("/{itemId}")
    public Item updateItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                           @PathVariable("itemId") Long id,
                           @RequestBody ItemDto dto) {
        return itemService.updateItem(userId, id, dto);
    }

    /**
     * Получает информацию о предмете по его идентификатору.
     *
     * @param id уникальный идентификатор предмета.
     * @return объект DTO, содержащий информацию о предмете и его комментариях.
     */
    @GetMapping("/{itemId}")
    public ItemDto getItemDtoById(@PathVariable("itemId") Long id) {
        return ItemMapper.mapToDtoWithComments(itemService.getItem(id), itemService);
    }

    /**
     * Получает все предметы, принадлежащие указанному пользователю.
     *
     * @param userId уникальный идентификатор пользователя.
     * @return список объектов DTO, представляющих предметы владельца.
     */
    @GetMapping
    public List<ItemOwnerDto> getAllItemsOfOwner(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getAllItemsOfOwner(userId)
                .stream()
                .map(item -> ItemMapper.mapToDtoOwner(item, bookingService, itemService))
                .toList();
    }

    /**
     * Ищет предметы по заданному текстовому запросу.
     *
     * @param text текст для поиска предметов.
     * @return список объектов DTO, представляющих найденные предметы.
     */
    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestParam("text") String text) {
        return itemService.searchItems(text)
                .stream().map(ItemMapper::mapToDto)
                .toList();
    }

    /**
     * Добавляет комментарий к предмету.
     *
     * @param authorId уникальный идентификатор пользователя, оставляющего комментарий.
     * @param itemId   уникальный идентификатор предмета, к которому добавляется комментарий.
     * @param dto      объект, содержащий данные комментария.
     * @return объект DTO, представляющий добавленный комментарий.
     */
    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@RequestHeader("X-Sharer-User-Id") Long authorId, @PathVariable("itemId") Long itemId,
                                 @RequestBody CommentDto dto) {
        return CommentMapper.mapToCommentDto(itemService.addComment(authorId, itemId, dto));
    }
}
