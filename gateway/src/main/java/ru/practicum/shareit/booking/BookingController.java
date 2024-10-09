package ru.practicum.shareit.booking;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.exception.ValidationException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Контроллер для управления бронированиями.
 * <p>
 * Этот контроллер обрабатывает HTTP-запросы, связанные с бронированием предметов,
 * включая создание бронирования, изменение статуса, получение информации о бронированиях
 * и получение бронирований владельца.
 * </p>
 */
@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    /**
     * Создает новое бронирование для указанного пользователя.
     *
     * @param userId     идентификатор пользователя, создающего бронирование
     * @param requestDto DTO с данными о бронировании
     * @return ResponseEntity с результатом операции
     * @throws ValidationException если время начала или окончания бронирования некорректно
     */
    @PostMapping
    public ResponseEntity<Object> bookItem(@Positive @RequestHeader("X-Sharer-User-Id") long userId,
                                           @Validated @RequestBody BookItemRequestDto requestDto) {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        if (requestDto.getStart().isBefore(now) || (!requestDto.getEnd().isAfter(requestDto.getStart()))) {
            throw new ValidationException("Время начала бронирования должно быть раньше времени окончания, " +
                    "и оба времени не могут быть в прошлом.");
        }
        log.info("Проверка на пересечение времени прошла успешно");
        log.info("Текущее время {}", now);
        log.info("Время начала {} и окончания {} брони", requestDto.getStart(), requestDto.getEnd());
        log.info("Создаем бронь предмета id={}, userId={}", requestDto.getItemId(), userId);
        return bookingClient.bookItem(userId, requestDto);
    }

    /**
     * Изменяет статус существующего бронирования.
     *
     * @param id       идентификатор бронирования
     * @param approved новый статус одобрения
     * @param userId   идентификатор пользователя, изменяющего статус
     * @return ResponseEntity с результатом операции
     */
    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> changeStatus(@Positive @PathVariable("bookingId") long id,
                                               @RequestParam boolean approved,
                                               @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Смена статуса бронирования id: {}, статус approved: {}, userId: {}", id, approved, userId);
        return bookingClient.changeStatus(id, approved, userId);
    }

    /**
     * Получает информацию о конкретном бронировании по его идентификатору.
     *
     * @param userId    идентификатор пользователя
     * @param bookingId идентификатор бронирования
     * @return ResponseEntity с результатом операции
     */
    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBooking(@Positive @RequestHeader("X-Sharer-User-Id") long userId,
                                             @Positive @PathVariable Long bookingId) {
        log.info("Получаем бронь id={}, userId={}", bookingId, userId);
        return bookingClient.getBooking(userId, bookingId);
    }

    /**
     * Получает список бронирований для указанного пользователя с заданным состоянием.
     *
     * @param userId     идентификатор пользователя
     * @param stateParam состояние бронирования
     * @param from       индекс первого элемента для пагинации
     * @param size       количество элементов на странице
     * @return ResponseEntity с результатом операции
     */
    @GetMapping
    public ResponseEntity<Object> getBookings(@Positive @RequestHeader("X-Sharer-User-Id") long userId,
                                              @RequestParam(name = "state", defaultValue = "all") String stateParam,
                                              @PositiveOrZero @RequestParam(name = "from", defaultValue = "0")
                                              Integer from,
                                              @Positive @RequestParam(name = "size", defaultValue = "10")
                                              Integer size) {
        BookingState state = BookingState.from(stateParam)
                .orElseThrow(() -> new IllegalArgumentException("Unknown state: " + stateParam));
        log.info("Получаем брони в state {}, userId={}, from={}, size={}", stateParam, userId, from, size);
        return bookingClient.getBookings(userId, state, from, size);
    }

    /**
     * Получает список бронирований, принадлежащих владельцу, с заданным состоянием.
     *
     * @param userId     идентификатор владельца
     * @param stateParam состояние бронирования
     * @return ResponseEntity с результатом операции
     */
    @GetMapping("/owner")
    public ResponseEntity<Object> getBookingsOFOwner(@Positive @RequestHeader("X-Sharer-User-Id") Long userId,
                                                     @RequestParam(name = "state", defaultValue = "all")
                                                     String stateParam) {
        BookingState state = BookingState.from(stateParam)
                .orElseThrow(() -> new IllegalArgumentException("Unknown state: " + stateParam));
        log.info("Получаем брони владельца в state {}, userId={}", stateParam, userId);
        return bookingClient.getOwnerBookings(userId, state);
    }
}
