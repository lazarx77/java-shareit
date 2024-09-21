package ru.practicum.shareit.booking;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingAddDto;
import ru.practicum.shareit.booking.dto.State;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Transactional(readOnly = true)
public interface BookingService {

    @Transactional
    Booking addBooking(BookingAddDto bookingDto, Long userId);

    @Transactional
    Booking changeStatus(Long id, Boolean approved, Long userId);

    Booking findSpecificBooking(Long id, Long userId);

    List<Booking> findAllBookingsOfBooker(Long userId, State state);

    List<Booking> findAllBookingsOfOwner(Long userId, State state);

    Booking findLastBooking(Item item);

    Booking findFutureBooking(Item item);
}
