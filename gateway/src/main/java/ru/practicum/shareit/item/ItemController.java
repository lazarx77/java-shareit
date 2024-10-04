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
        return itemClient.addItem(userId, dto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@Positive @RequestHeader("X-Sharer-User-Id") Long userId,
                                             @Positive @PathVariable("itemId") Long id,
                                             @RequestBody ItemRequestDto dto) {
        return itemClient.updateItem(userId, id, dto);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getById(@Positive @RequestHeader("X-Sharer-User-Id") Long userId,
                                          @Positive @PathVariable("itemId") Long id) {
        return itemClient.getById(userId, id);
    }

    @GetMapping
    public ResponseEntity<Object> getItemsOfOwner(@Positive @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemClient.getItemsOfOwner(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItems(@Positive @RequestHeader("X-Sharer-User-Id") Long userId,
                                              @RequestParam("text") String text) {
        return itemClient.searchItems(userId, text);
    }

    @PostMapping("/{itemId}/comments")
    public ResponseEntity<Object> addComment(@Positive @RequestHeader("X-Sharer-User-Id") Long authorId,
                                             @Positive @PathVariable("itemId") Long itemId,
                                             @RequestBody CommentDto dto) {
        return itemClient.addComment(authorId, itemId, dto);
    }
}
