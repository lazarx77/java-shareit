package ru.practicum.shareit.item;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

/**
 * Контроллер для управления предметами.
 * <p>
 * Этот контроллер обрабатывает HTTP-запросы, связанные с предметами,
 * включая добавление, обновление, получение информации о предмете,
 * получение предметов владельца, поиск предметов и добавление комментариев.
 * </p>
 */
@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemClient itemClient;

    /**
     * Добавляет новый предмет для указанного пользователя.
     *
     * @param userId идентификатор пользователя, добавляющего предмет
     * @param dto    DTO с данными о предмете
     * @return ResponseEntity с результатом операции
     */
    @PostMapping
    public ResponseEntity<Object> addItem(@Positive @RequestHeader("X-Sharer-User-Id") Long userId,
                                          @Validated @RequestBody ItemDto dto) {
        log.info("Добавление предмета пользователем userId={}", userId);
        return itemClient.addItem(userId, dto);
    }

    /**
     * Обновляет информацию о существующем предмете.
     *
     * @param userId идентификатор пользователя, обновляющего предмет
     * @param id     идентификатор предмета
     * @param dto    DTO с новыми данными о предмете
     * @return ResponseEntity с результатом операции
     */
    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@Positive @RequestHeader("X-Sharer-User-Id") Long userId,
                                             @Positive @PathVariable("itemId") Long id,
                                             @RequestBody ItemDto dto) {
        log.info("Обновление предмета пользователем userId={}", userId);
        return itemClient.updateItem(userId, id, dto);
    }

    /**
     * Получает информацию о предмете по его идентификатору.
     *
     * @param userId идентификатор пользователя
     * @param id     идентификатор предмета
     * @return ResponseEntity с результатом операции
     */
    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getById(@Positive @RequestHeader("X-Sharer-User-Id") Long userId,
                                          @Positive @PathVariable("itemId") Long id) {
        log.info("Получение предмета id={} пользователем userId={}", id, userId);
        return itemClient.getById(userId, id);
    }

    /**
     * Получает список предметов, принадлежащих указанному пользователю.
     *
     * @param userId идентификатор владельца предметов
     * @return ResponseEntity с результатом операции
     */
    @GetMapping
    public ResponseEntity<Object> getItemsOfOwner(@Positive @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Обновление всех своих предметов пользователем userId={}", userId);
        return itemClient.getItemsOfOwner(userId);
    }

    /**
     * Ищет предметы по заданному тексту.
     *
     * @param userId идентификатор пользователя
     * @param text   текст для поиска
     * @return ResponseEntity с результатом операции
     */
    @GetMapping("/search")
    public ResponseEntity<Object> searchItems(@Positive @RequestHeader("X-Sharer-User-Id") Long userId,
                                              @RequestParam("text") String text) {
        log.info("Поиск предметов пользователем userId={}", userId);
        return itemClient.searchItems(userId, text);
    }

    /**
     * Добавляет комментарий к предмету.
     *
     * @param authorId идентификатор автора комментария
     * @param itemId   идентификатор предмета
     * @param dto      DTO с данными комментария
     * @return ResponseEntity с результатом операции
     */
    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@Positive @RequestHeader("X-Sharer-User-Id") Long authorId,
                                             @Positive @PathVariable("itemId") Long itemId,
                                             @Validated @RequestBody CommentDto dto) {
        log.info("Добавление комментария пользователем userId={} для предмета Id={}", authorId, itemId);
        return itemClient.addComment(authorId, itemId, dto);
    }
}
