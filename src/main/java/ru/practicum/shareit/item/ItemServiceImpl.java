package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.exception.ItemDoNotBelongToUser;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.UserValidatorService;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Item addNewItem(Long userId, ItemDto dto) {
        UserValidatorService.validateId(userId);
        log.info("Проверка на наличие пользователя с id: {} ", userId);
        User user = userService.findUserById(userId);
        log.info("Сохраняем вещь с id пользователя: {} ", userId);
        Item item = ItemMapper.mapToItem(dto);
        item.setOwner(user);

        return itemRepository.save(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item updateItem(Long userId, Long itemId, ItemDto dto) {
        UserValidatorService.validateId(userId);
        ItemValidatorService.validateId(itemId);
        log.info("Проверка на наличие пользователя с id: {} ", userId);
        userService.findUserById(userId);
        log.info("Обновление вещи с id: {} ", itemId);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь с id = " + itemId + " не найдена"));
        if (!item.getOwner().getId().equals(userId)) {
            throw new ItemDoNotBelongToUser("Вещь с id = " + itemId + " не принадлежит пользователю с id = " + userId);
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
        ItemValidatorService.validateId(itemId);
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь с id = " + itemId + " не найдена"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Item> getAllItemsOfOwner(Long userId) {
        UserValidatorService.validateId(userId);
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
        return itemRepository.search(text).stream().filter(Item::isAvailable).toList();
    }

    @Override
    public Comment addComment(Long authorId, Long itemId, CommentDto dto) {
        log.info("Добавление комментария для вещи с id= " + itemId);
        UserValidatorService.validateId(authorId);
        ItemValidatorService.validateId(itemId);
        Booking booking = bookingRepository.findByItemIdAndBookerIdAndEndBefore(itemId, authorId, LocalDateTime.now())
                .orElseThrow(()-> new ValidationException("Бронь с указанными параметрами не существует>"));
        User booker = booking.getBooker();
        Comment comment = new Comment();
        comment.setItem(booking.getItem());
        comment.setAuthor(booker);
        comment.setText(dto.getText());
        comment.setCreated(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getComments(Long itemId) {
        return commentRepository.findAllByItemId(itemId);
    }
}
