package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingAddDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.dto.State;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.dto.ItemOwnerDto;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@AllArgsConstructor
@Slf4j
public class BookingController {

    BookingService bookingService;

    @PostMapping
    public Booking add(@RequestHeader("X-Sharer-User-Id") Long userId, @Validated @RequestBody BookingAddDto dto) {
//        log.info("В контроллер зашел dto=" + dto);
        return bookingService.addBooking(dto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto changeStatus(@PathVariable("bookingId") Long id,
                                @RequestParam Boolean approved,
                                @RequestHeader("X-Sharer-User-Id") Long userId){
        return BookingMapper.mapToBookingDto(bookingService.changeStatus(id, approved, userId));
    }

    @GetMapping("/{bookingId}")
    public BookingDto findSpecificBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                      @PathVariable("bookingId") Long id) {
        return BookingMapper.mapToBookingDto(bookingService.findSpecificBooking(id, userId));
    }

    @GetMapping
    public List<BookingDto> findAllBookingsOfBooker(@RequestHeader("X-Sharer-User-Id") Long userId,
                                            @RequestParam(defaultValue = "ALL") State state) {
        return bookingService
                .findAllBookingsOfBooker(userId, state).stream().map(BookingMapper::mapToBookingDto).toList();
    }

    @GetMapping("/owner")
    public List<BookingDto> findAllBookingsOfOwner(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                     @RequestParam(defaultValue = "ALL") State state) {
        return bookingService
                .findAllBookingsOfOwner(userId, state).stream().map(BookingMapper::mapToBookingDto).toList();
    }
}
