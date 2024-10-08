package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.ItemDoNotBelongToUser;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Класс ItemServiceImpl предоставляет
 * методы для работы с предметами.
 */
@Service
@AllArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final UserService userService;
    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;
    private final ItemRequestRepository itemRequestRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Item addNewItem(Long userId, ItemDto dto) {
        log.info("Проверка на наличие пользователя с id: {} ", userId);
        User user = userService.findUserById(userId);
        Item item = ItemMapper.mapToItem(dto);
        if (dto.getRequestId() != null) {
            log.info("Запрос вещи УКАЗАН, проверка на наличие запроса с id = " + dto.getRequestId() + " в БД");
            ItemRequest itemRequest = itemRequestRepository
                    .findById(dto.getRequestId())
                    .orElseThrow(() -> new NotFoundException("Запрос с id = " + dto.getRequestId() + " не найден"));
            item.setRequest(itemRequest);
        }
        log.info("Сохраняем предмет с id пользователя: {} ", userId);
        item.setOwner(user);

        return itemRepository.save(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item updateItem(Long userId, Long itemId, ItemDto dto) {
        log.info("Проверка на наличие пользователя с id: {} ", userId);
        userService.findUserById(userId);
        log.info("Обновление предмета с id: {} ", itemId);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмет с id = " + itemId + " не найден"));
        if (!item.getOwner().getId().equals(userId)) {
            throw new ItemDoNotBelongToUser("Предмет с id = " + itemId + " не принадлежит пользователю с id = " +
                    userId);
        }
        if (dto.getName() != null) {
            item.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            item.setDescription(dto.getDescription());
        }
        if (dto.isAvailable() != null) {
            item.setAvailable(dto.isAvailable());
        }
        return itemRepository.save(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item getItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмет с id = " + itemId + " не найден"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Item> getAllItemsOfOwner(Long userId) {
        return itemRepository.findByOwnerId(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Item> searchItems(String text) {
        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }
        return itemRepository.search(text)
                .stream()
                .filter(Item::isAvailable)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Comment addComment(Long authorId, Long itemId, CommentDto dto) {
        log.info("Добавление комментария для предмета с id={}, authorId={}, dto={}", itemId, authorId, dto);
        userService.findUserById(authorId);
        itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Предмета не существует id=" + itemId));
        Booking booking = bookingRepository.findByItemIdAndBookerIdAndEndBefore(itemId, authorId, LocalDateTime.now())
                .orElseThrow(() -> new ValidationException("Бронь с указанными параметрами не существует - " +
                        "при добавлении комментария"));
        User booker = booking.getBooker();
        Comment comment = new Comment();
        comment.setItem(booking.getItem());
        comment.setAuthor(booker);
        comment.setText(dto.getText());
        comment.setCreated(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Comment> getComments(Long itemId) {
        return commentRepository.findAllByItemId(itemId);
    }

    @Override
    public List<Item> getItemsByRequestId(Long requestId) {
        return itemRepository.findAllByRequestId(requestId);
    }
}
