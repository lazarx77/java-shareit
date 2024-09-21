package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.ItemService;

import java.time.LocalDateTime;

/**
 * Утилитный класс для преобразования объектов бронирования между различными представлениями (DTO).
 * <p>
 * Этот класс содержит статические методы для преобразования сущностей бронирования в DTO
 * и наоборот.
 */
public class BookingMapper {

    /**
     * Преобразует объект Booking в BookingDto.
     *
     * @param booking объект бронирования, который нужно преобразовать
     * @return объект BookingDto, содержащий данные из объекта Booking
     */
    public static BookingDto mapToBookingDto(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setBooker(booking.getBooker());
        dto.setItem(booking.getItem());
        dto.setStatus(booking.getStatus());
        dto.setStart(booking.getStart());
        dto.setEnd(booking.getEnd());
        dto.setState(setState(booking));
        return dto;
    }

    /**
     * Преобразует объект Booking в BookingInItemDto.
     *
     * @param booking объект бронирования, который нужно преобразовать
     * @return объект BookingInItemDto, содержащий данные из объекта Booking
     */
    public static BookingInItemDto mapToItemBookingDto(Booking booking) {
        BookingInItemDto dto = new BookingInItemDto();
        dto.setId(booking.getId());
        dto.setBookerId(booking.getBooker().getId());
        dto.setStatus(booking.getStatus());
        dto.setStart(booking.getStart());
        dto.setEnd(booking.getEnd());
        dto.setState(setState(booking));
        return dto;
    }

    /**
     * Преобразует объект BookingAddDto в объект Booking.
     *
     * @param dto объект BookingAddDto, содержащий данные для создания нового бронирования
     * @param itemService сервис для получения информации о предмете
     * @return объект Booking, созданный на основе данных из BookingAddDto
     */
    public static Booking mapToBookingFromAddDto(BookingAddDto dto, ItemService itemService) {
        Booking booking = new Booking();
        booking.setBooker(dto.getBooker());
        booking.setItem(itemService.getItem(dto.getItemId()));
        booking.setStatus(dto.getStatus());
        booking.setEnd(dto.getEnd());
        booking.setStart(dto.getStart());
        return booking;
    }

    /**
     * Устанавливает состояние бронирования на основе его статуса и временных рамок.
     *
     * @param booking объект бронирования, для которого нужно определить состояние
     * @return состояние бронирования (REJECTED, WAITING, PAST, FUTURE, CURRENT)
     */
    private static State setState(Booking booking){
        State state;
        if (booking.getStatus() == Status.REJECTED) {
            state = State.REJECTED;
        } else if (booking.getStatus() == Status.WAITING) {
            state = State.WAITING;
        } else if (booking.getEnd().isBefore(LocalDateTime.now())) {
            state = State.PAST;
        } else if (booking.getStart().isAfter(LocalDateTime.now())) {
            state = State.FUTURE;
        } else {
            state = State.CURRENT;
        }
        return state;
    }
}
