package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingValidatorService;
import ru.practicum.shareit.booking.Status;

import java.time.LocalDateTime;

public class BookingMapper {

    public static BookingDto mapToBookingDto(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setBooker(booking.getBooker());
        dto.setItem(booking.getItem());
        dto.setStatus(booking.getStatus());
        dto.setStartDate(booking.getStartDate());
        dto.setEndDate(booking.getEndDate());
        State state;
        if (booking.getStatus() == Status.REJECTED) {
            state = State.REJECTED;
        } else if (booking.getStatus() == Status.WAITING) {
            state = State.WAITING;
        } else if (booking.getEndDate().isBefore(LocalDateTime.now())) {
            state = State.PAST;
        } else if (booking.getStartDate().isAfter(LocalDateTime.now())) {
            state = State.FUTURE;
        } else {
            state = State.CURRENT;
        }
        dto.setState(state);
        return dto;
    }

    public static Booking mapToBooking(BookingDto dto) {
        BookingValidatorService.isEndDateAfterStartDate(dto.getStartDate(), dto.getEndDate());
        Booking booking = new Booking();
        booking.setBooker(dto.getBooker());
        booking.setItem(dto.getItem());
        booking.setStatus(dto.getStatus());
        booking.setEndDate(dto.getEndDate());
        booking.setStartDate(dto.getStartDate());
        return booking;
    }
}
