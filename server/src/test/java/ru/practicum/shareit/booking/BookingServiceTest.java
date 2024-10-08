package ru.practicum.shareit.booking;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.dto.BookingAddDto;
import ru.practicum.shareit.booking.dto.State;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.ItemDoNotBelongToUser;
import ru.practicum.shareit.exception.NotAvailableException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserService userService;

    @Mock
    private ItemService itemService;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private User user;
    private User owner;
    private Item item;
    private BookingAddDto bookingAddDto;

    @BeforeEach
    void setUp() {
        user = new User(1L, "User", "user@example.com");
        owner = new User(2L, "Owner", "owner@email.com");
        item = new Item(1L, "Item", "Description", true, owner, null);
        bookingAddDto = new BookingAddDto(1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2),
                item.getId(), user, Status.WAITING, State.WAITING);
    }

    @Test
    void addBooking_whenItemIsAvailable_thenBookingIsSaved() {
        when(userService.findUserById(user.getId())).thenReturn(user);
        when(itemService.getItem(item.getId())).thenReturn(item);
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Booking actualBooking = bookingService.addBooking(bookingAddDto, user.getId());

        assertEquals(Status.WAITING, actualBooking.getStatus());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void addBooking_whenItemIsNotAvailable_thenNotAvailableExceptionThrown() {
        item.setAvailable(false);
        when(userService.findUserById(user.getId())).thenReturn(user);
        when(itemService.getItem(item.getId())).thenReturn(item);

        assertThrows(NotAvailableException.class, () -> bookingService.addBooking(bookingAddDto, user.getId()));
    }

    @Test
    void changeStatus_whenUserIsOwner_thenStatusIsChanged() {
        Booking booking = new Booking(1L, LocalDateTime.now(), LocalDateTime.now().plusDays(1), item, owner,
                Status.WAITING);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Booking updatedBooking = bookingService.changeStatus(1L, true, owner.getId());

        assertEquals(Status.APPROVED, updatedBooking.getStatus());
        verify(bookingRepository, times(1)).save(updatedBooking);
    }

    @Test
    void changeStatus_whenUserIsNotOwner_thenItemDoNotBelongToUserExceptionThrown() {
        User anotherUser = new User(2L, "Another User", "another@example.com");
        Booking booking = new Booking(1L, LocalDateTime.now(), LocalDateTime.now().plusDays(1), item, user,
                Status.WAITING);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        assertThrows(ItemDoNotBelongToUser.class, () -> bookingService
                .changeStatus(1L, true, user.getId()));
    }

    @Test
    void findSpecificBooking_whenUserIsBookerOrOwner_thenBookingIsReturned() {
        Booking booking = new Booking(1L, LocalDateTime.now(), LocalDateTime.now().plusDays(1), item, user,
                Status.WAITING);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        Booking actualBooking = bookingService.findSpecificBooking(1L, user.getId());

        assertEquals(booking, actualBooking);
    }

    @Test
    void findAllBookingsOfBooker_whenUserExists_thenListOfBookingsReturned() {
        when(userService.findUserById(user.getId())).thenReturn(user);
        Booking booking1 = new Booking(1L, LocalDateTime.now(), LocalDateTime.now().plusDays(1), item, user,
                Status.WAITING);
        Booking booking2 = new Booking(2L, LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(4), item,
                user, Status.APPROVED);
        when(bookingRepository.findAllBookingsByBooker_idOrderByStartDesc(user.getId()))
                .thenReturn(List.of(booking1, booking2));

        List<Booking> bookings = bookingService.findAllBookingsOfBooker(user.getId(), State.ALL);

        assertEquals(2, bookings.size());
        assertEquals(booking1, bookings.get(0));
        assertEquals(booking2, bookings.get(1));
    }

    @Test
    void findAllBookingsOfBooker_whenNoBookingsExist_thenEmptyListReturned() {
        when(userService.findUserById(user.getId())).thenReturn(user);
        when(bookingRepository.findAllBookingsByBooker_idOrderByStartDesc(user.getId()))
                .thenReturn(Collections.emptyList());

        List<Booking> actualBookings = bookingService
                .findAllBookingsOfBooker(user.getId(), State.ALL);

        assertTrue(actualBookings.isEmpty());
    }

    @Test
    void findAllBookingsOfOwner_whenUserExists_thenListOfBookingsReturned() {
        Booking booking1 = new Booking(1L, LocalDateTime.now(), LocalDateTime.now().plusDays(1), item, user,
                Status.WAITING);
        Booking booking2 = new Booking(2L, LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(4), item,
                user, Status.APPROVED);
        when(bookingRepository.findAllByItem_Owner_idOrderByStartDesc(user.getId()))
                .thenReturn(List.of(booking1, booking2));

        List<Booking> bookings = bookingService.findAllBookingsOfOwner(user.getId(), State.ALL);

        assertEquals(2, bookings.size());
        assertEquals(booking1, bookings.get(0));
        assertEquals(booking2, bookings.get(1));
    }

    @Test
    void findAllBookingsOfOwner_whenNoBookingsExist_thenItemDoNotBelongToUserExceptionThrown() {
        when(bookingRepository.findAllByItem_Owner_idOrderByStartDesc(user.getId())).thenReturn(Collections.emptyList());

        assertThrows(ItemDoNotBelongToUser.class, () -> bookingService.findAllBookingsOfOwner(user.getId(), State.ALL));
    }

    @SneakyThrows
    @Test
    void findLastBooking_whenBookingExists_thenLastBookingReturned() {
        when(itemService.getItem(item.getId())).thenReturn(item);
        Booking lastBooking = new Booking(1L, LocalDateTime.now().plusSeconds(1),
                LocalDateTime.now().plusSeconds(2), item, owner, Status.WAITING);
        when(bookingRepository.findFirstByItem_Owner_idAndStartBeforeOrderByStartDesc(anyLong(),
                any(LocalDateTime.class)))
                .thenReturn(Optional.of(lastBooking));

        Booking foundBooking = bookingService.findLastBooking(item);

        assertEquals(lastBooking, foundBooking);
    }

    @Test
    void findLastBooking_whenNoBookingExists_thenNullReturned() {
        when(itemService.getItem(item.getId())).thenReturn(item);
        when(bookingRepository.findFirstByItem_Owner_idAndStartBeforeOrderByStartDesc(anyLong(),
                any(LocalDateTime.class)))
                .thenReturn(Optional.empty());

        Booking foundBooking = bookingService.findLastBooking(item);

        assertNull(foundBooking);
    }

    @Test
    void findFutureBooking_whenFutureBookingExists_thenBookingReturned() {
        when(itemService.getItem(item.getId())).thenReturn(item);
        Booking futureBooking = new Booking(1L, LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2), item, owner, Status.WAITING);

        when(bookingRepository.findFirstByItem_Owner_idAndStartAfterOrderByStartAsc(anyLong(),
                any(LocalDateTime.class)))
                .thenReturn(Optional.of(futureBooking));

        Booking foundBooking = bookingService.findFutureBooking(item);

        assertEquals(futureBooking, foundBooking);
    }

    @Test
    void findFutureBooking_whenNoFutureBookingExists_thenNullReturned() {
        when(itemService.getItem(item.getId())).thenReturn(item);
        when(bookingRepository.findFirstByItem_Owner_idAndStartAfterOrderByStartAsc(anyLong(),
                any(LocalDateTime.class)))
                .thenReturn(Optional.empty());

        Booking foundBooking = bookingService.findFutureBooking(item);

        assertNull(foundBooking);
    }
}
