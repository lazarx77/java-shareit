package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.State;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface BookingService {

    Booking addBooking(BookingDto bookingDto, Long userId);

    Booking changeStatus(Long id, Boolean approved, Long userId);

    Booking findSpecificBooking(Long id, Long userId);

    List<Booking> findAllBookingsOfBooker(Long userId, State state);

    List<Booking> findAllBookingsOfOwner(Long userId, State state);

    Set<LocalDateTime> lastDates(Long itemId);

    public Set<LocalDateTime> futureDates(Long itemId);

}
