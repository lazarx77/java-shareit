package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.ItemDoNotBelongToUser;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Тестовый класс для проверки функциональности сервиса ItemService.
 */
@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ItemRequestRepository itemRequestRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    private User user;
    private Item item;
    private ItemDto itemDto;

    @BeforeEach
    void setUp() {
        user = new User(1L, "User", "user@example.com");
        item = new Item(1L, "Item", "Description", true, user, null);
        itemDto = new ItemDto(1L, "Item", "Description",
                true, null, null, null, null);
    }

    @Test
    void addNewItem_whenUserExists_thenItemIsSaved() {
        when(userService.findUserById(1L)).thenReturn(user);
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item result = itemService.addNewItem(1L, itemDto);

        assertNotNull(result);
        assertEquals(item.getId(), result.getId());
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    void addNewItem_whenRequestIdProvided_thenItemIsLinkedToRequest() {
        user = new User(1L, "User", "user@example.com");
        ItemRequest itemRequest = new ItemRequest(1L, "description", user, LocalDateTime.now());
        itemDto.setRequestId(1L);
        item.setRequest(itemRequest);

        when(userService.findUserById(1L)).thenReturn(user);
        when(itemRequestRepository.findById(1L)).thenReturn(Optional.of(itemRequest));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item result = itemService.addNewItem(1L, itemDto);

        assertNotNull(result);
        assertEquals(itemRequest, result.getRequest());
        verify(itemRequestRepository, times(1)).findById(1L);
    }

    @Test
    void updateItem_whenItemDoesNotBelongToUser_thenThrowItemDoNotBelongToUserException() {
        Item anotherUserItem = new Item(2L, "Another Item", "Description",
                true, new User(2L, "Another User", "another@example.com"), null);
        when(userService.findUserById(1L)).thenReturn(user);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(anotherUserItem));

        ItemDto updatedItemDto = new ItemDto(1L, "Updated Item", "Updated Description",
                true, null, null, null, null);

        Exception exception = assertThrows(ItemDoNotBelongToUser.class, () -> {
            itemService.updateItem(1L, 1L, updatedItemDto);
        });

        assertEquals("Предмет с id = 1 не принадлежит пользователю с id = 1", exception.getMessage());
    }

    @Test
    void updateItem_whenItemDoesNotExist_thenThrowNotFoundException() {
        when(userService.findUserById(1L)).thenReturn(user);
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        ItemDto updatedItemDto = new ItemDto(1L, "Updated Item", "Updated Description",
                true, null, null, null, null);

        Exception exception = assertThrows(NotFoundException.class, () -> {
            itemService.updateItem(1L, 1L, updatedItemDto);
        });

        assertEquals("Предмет с id = 1 не найден", exception.getMessage());
    }

    @Test
    void getItem_whenItemExists_thenReturnItem() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        Item result = itemService.getItem(1L);

        assertNotNull(result);
        assertEquals(item.getId(), result.getId());
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    void getItem_whenItemDoesNotExist_thenThrowNotFoundException() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            itemService.getItem(1L);
        });

        assertEquals("Предмет с id = 1 не найден", exception.getMessage());
    }

    @Test
    void getAllItemsOfOwner_whenItemsExist_thenReturnListOfItems() {
        when(itemRepository.findByOwnerId(1L)).thenReturn(Collections.singletonList(item));

        List<Item> result = itemService.getAllItemsOfOwner(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(item.getId(), result.get(0).getId());
        verify(itemRepository, times(1)).findByOwnerId(1L);
    }

    @Test
    void searchItems_whenSearchTextIsValid_thenReturnListOfAvailableItems() {
        when(itemRepository.search("Item")).thenReturn(Collections.singletonList(item));

        List<Item> result = itemService.searchItems("Item");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(item.getId(), result.get(0).getId());
        verify(itemRepository, times(1)).search("Item");
    }

    @Test
    void searchItems_whenSearchTextIsBlank_thenReturnEmptyList() {
        List<Item> result = itemService.searchItems(" ");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(itemRepository, never()).search(anyString());
    }

    @Test
    void addComment_whenBookingExists_thenCommentIsSaved() {
        Long itemId = 1L;
        Long authorId = 2L;

        Item item = new Item();
        item.setId(itemId);
        item.setName("Test Item");
        item.setDescription("Test Description");
        item.setAvailable(true);
        item.setOwner(user);

        User author = new User(authorId, "authorName", "author@email.ru");

        CommentDto commentDto = new CommentDto();
        commentDto.setText("This is a comment");

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setItem(item);
        booking.setBooker(user); // Предполагается, что user уже создан в setUp()
        booking.setStart(LocalDateTime.of(2012, 1, 1, 10, 10));
        booking.setEnd(LocalDateTime.of(2012, 2, 1, 10, 10));
        booking.setStatus(Status.WAITING);

        when(userService.findUserById(authorId)).thenReturn(author);
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        when(bookingRepository.findByItemIdAndBookerIdAndEndBefore(eq(itemId), eq(authorId), any(LocalDateTime.class)))
                .thenReturn(Optional.of(booking));

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText(commentDto.getText());
        comment.setItem(item);
        comment.setAuthor(user);
        comment.setCreated(LocalDateTime.now());

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        Comment result = itemService.addComment(authorId, itemId, commentDto);

        assertNotNull(result);
        assertEquals(commentDto.getText(), result.getText());
        assertEquals(item, result.getItem());
        assertEquals(user, result.getAuthor());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void addComment_whenBookingDoesNotExist_thenThrowValidationException() {
        Long itemId = 1L;
        Long authorId = 2L;

        Item item = new Item();
        item.setId(itemId);
        item.setName("Test Item");
        item.setDescription("Test Description");
        item.setAvailable(true);
        item.setOwner(user);

        User author = new User(authorId, "authorName", "author@email.ru");

        CommentDto commentDto = new CommentDto();
        commentDto.setText("This is a comment");

        when(userService.findUserById(anyLong())).thenReturn(author);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(bookingRepository.findByItemIdAndBookerIdAndEndBefore(anyLong(), anyLong(), any(LocalDateTime.class)))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(ValidationException.class, () -> {
            itemService.addComment(1L, 1L, commentDto);
        });

        assertEquals("Бронь с указанными параметрами не существует - при добавлении комментария",
                exception.getMessage());
    }

    @Test
    void getComments_whenCommentsExist_thenReturnListOfComments() {
        when(commentRepository.findAllByItemId(1L)).thenReturn(Collections.singletonList(new Comment()));

        List<Comment> result = itemService.getComments(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(commentRepository, times(1)).findAllByItemId(1L);
    }

    @Test
    void getItemsByRequestId_whenItemsExist_thenReturnListOfItems() {
        when(itemRepository.findAllByRequestId(1L)).thenReturn(Collections.singletonList(item));

        List<Item> result = itemService.getItemsByRequestId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(item.getId(), result.get(0).getId());
        verify(itemRepository, times(1)).findAllByRequestId(1L);
    }
}
