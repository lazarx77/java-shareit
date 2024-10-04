package ru.practicum.shareit.item;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> addItem(@Positive @RequestHeader("X-Sharer-User-Id") Long userId,
                                          @Validated @RequestBody ItemRequestDto dto) {
        log.info("Добавление предмета пользователем userId={}", userId);
        return itemClient.addItem(userId, dto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@Positive @RequestHeader("X-Sharer-User-Id") Long userId,
                                             @Positive @PathVariable("itemId") Long id,
                                             @RequestBody ItemRequestDto dto) {
        log.info("Обновление предмета пользователем userId={}", userId);
        return itemClient.updateItem(userId, id, dto);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getById(@Positive @RequestHeader("X-Sharer-User-Id") Long userId,
                                          @Positive @PathVariable("itemId") Long id) {
        log.info("Получение предмета id={} пользователем userId={}", id, userId);
        return itemClient.getById(userId, id);
    }

    @GetMapping
    public ResponseEntity<Object> getItemsOfOwner(@Positive @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Обновление всех своих предметов пользователем userId={}", userId);
        return itemClient.getItemsOfOwner(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItems(@Positive @RequestHeader("X-Sharer-User-Id") Long userId,
                                              @RequestParam("text") String text) {
        log.info("Поиск предметов пользователем userId={}", userId);
        return itemClient.searchItems(userId, text);
    }

    @PostMapping("/{itemId}/comments")
    public ResponseEntity<Object> addComment(@Positive @RequestHeader("X-Sharer-User-Id") Long authorId,
                                             @Positive @PathVariable("itemId") Long itemId,
                                             @RequestBody CommentDto dto) {
        log.info("Добавление комментария пользователем userId={} для предмета Id={}", authorId, itemId);
        return itemClient.addComment(authorId, itemId, dto);
    }
}
