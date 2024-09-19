package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.dto.ItemOwnerDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

/**
 * Класс ItemController является контроллером REST, который обрабатывает HTTP-запросы,
 * связанные с предметами в системе. Он предоставляет конечные точки для добавления, обновления,
 * получения и поиска предметов. Контроллер использует сервисный слой для выполнения бизнес-логики
 * и возвращает данные в формате JSON.
 */
@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final BookingService bookingService;

    /**
     * Обрабатывает POST-запрос для добавления нового предмета.
     *
     * @param userId идентификатор пользователя, добавляющего предмет.
     * @param dto    объект типа {@link ItemDto}, содержащий данные о предмете.
     * @return добавленный объект типа {@link Item}.
     */
    @PostMapping
    public Item add(@RequestHeader("X-Sharer-User-Id") Long userId,
                    @Validated @RequestBody ItemDto dto) {
        return itemService.addNewItem(userId, dto);
    }

    /**
     * Обрабатывает PATCH-запрос для обновления существующего предмета.
     *
     * @param userId идентификатор пользователя, пытающегося обновить предмет.
     * @param id     идентификатор предмета, который необходимо обновить.
     * @param dto    объект типа {@link ItemDto}, содержащий новые данные о предмете.
     * @return обновленный объект типа {@link Item}.
     */
    @PatchMapping("/{itemId}")
    public Item updateItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                           @PathVariable("itemId") Long id,
                           @RequestBody ItemDto dto) {
        return itemService.updateItem(userId, id, dto);
    }

    /**
     * Обрабатывает GET-запрос для получения предмета по его идентификатору.
     *
     * @param id идентификатор предмета, который необходимо получить.
     * @return объект типа {@link ItemDto}, содержащий данные о предмете.
     */
    @GetMapping("/{itemId}")
    public ItemDto getItemDtoById(@PathVariable("itemId") Long id) {
        return ItemMapper.mapToDto(itemService.getItem(id));
    }

    /**
     * Обрабатывает GET-запрос для получения всех предметов, принадлежащих указанному владельцу.
     *
     * @param userId идентификатор владельца, чьи предметы необходимо получить.
     * @return список объектов типа {@link ItemOwnerDto}, содержащих данные о предметах владельца.
     */
    @GetMapping
    public List<ItemOwnerDto> getAllItemsOfOwner(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getAllItemsOfOwner(userId).stream()
                .map(item -> ItemMapper.mapToDtoOwner(item, bookingService)).toList();
    }

    /**
     * Обрабатывает GET-запрос для поиска предметов по текстовому запросу.
     *
     * @param text текст для поиска предметов.
     * @return список объектов типа {@link ItemDto}, соответствующих критериям поиска.
     */
    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestParam("text") String text) {
        return itemService.searchItems(text).stream().map(ItemMapper::mapToDto).toList();
    }
}
