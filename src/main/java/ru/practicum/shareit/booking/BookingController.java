package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingAddDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.dto.State;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

/**
 * Контроллер для управления бронированиями.
 * <p>
 * Этот контроллер обрабатывает HTTP-запросы, связанные с созданием,
 * изменением и получением информации о бронированиях.
 */
@RestController
@RequestMapping(path = "/bookings")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    /**
     * Создает новое бронирование.
     *
     * @param userId идентификатор пользователя, осуществляющего бронирование
     * @param dto    объект, содержащий данные для создания нового бронирования
     * @return созданное бронирование
     */
    @PostMapping
    public Booking add(@RequestHeader("X-Sharer-User-Id") Long userId, @Validated @RequestBody BookingAddDto dto) {
        return bookingService.addBooking(dto, userId);
    }

    /**
     * Изменяет статус существующего бронирования.
     *
     * @param id       идентификатор бронирования, статус которого нужно изменить
     * @param approved новый статус бронирования (одобрено/отклонено)
     * @param userId   идентификатор пользователя, изменяющего статус
     * @return объект BookingDto с обновленным статусом
     */
    @PatchMapping("/{bookingId}")
    public BookingDto changeStatus(@PathVariable("bookingId") Long id,
                                   @RequestParam Boolean approved,
                                   @RequestHeader("X-Sharer-User-Id") Long userId) {
        return BookingMapper.mapToBookingDto(bookingService.changeStatus(id, approved, userId));
    }

    /**
     * Получает информацию о конкретном бронировании.
     *
     * @param userId идентификатор пользователя, запрашивающего информацию
     * @param id     идентификатор бронирования, информацию о котором нужно получить
     * @return объект BookingDto с данными о бронировании
     */
    @GetMapping("/{bookingId}")
    public BookingDto findSpecificBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                          @PathVariable("bookingId") Long id) {
        return BookingMapper.mapToBookingDto(bookingService.findSpecificBooking(id, userId));
    }

    /**
     * Получает список всех бронирований пользователя.
     *
     * @param userId идентификатор пользователя, для которого нужно получить список бронирований
     * @param state  состояние бронирований, которые нужно получить (ALL, CURRENT, PAST, FUTURE, WAITING, REJECTED)
     * @return список объектов BookingDto с данными о бронированиях
     */
    @GetMapping
    public List<BookingDto> findAllBookingsOfBooker(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                    @RequestParam(defaultValue = "ALL") State state) {
        return bookingService
                .findAllBookingsOfBooker(userId, state)
                .stream()
                .map(BookingMapper::mapToBookingDto)
                .toList();
    }

    /**
     * Получает список всех бронирований, связанных с предметами, принадлежащими пользователю.
     *
     * @param userId идентификатор владельца предметов
     * @param state  состояние бронирований, которые нужно получить (ALL, CURRENT, PAST, FUTURE, WAITING, REJECTED)
     * @return список объектов BookingDto с данными о бронированиях
     */
    @GetMapping("/owner")
    public List<BookingDto> findAllBookingsOfOwner(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                   @RequestParam(defaultValue = "ALL") State state) {
        return bookingService
                .findAllBookingsOfOwner(userId, state)
                .stream().map(BookingMapper::mapToBookingDto)
                .toList();
    }
}
