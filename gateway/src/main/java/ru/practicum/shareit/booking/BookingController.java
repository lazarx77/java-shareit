package ru.practicum.shareit.booking;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.booking.dto.BookingState;


@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
	private final BookingClient bookingClient;

	@PostMapping
	public ResponseEntity<Object> bookItem(@Positive @RequestHeader("X-Sharer-User-Id") long userId,
										   @RequestBody @Valid BookItemRequestDto requestDto) {
		log.info("Creating booking {}, userId={}", requestDto, userId);
		return bookingClient.bookItem(userId, requestDto);
	}

	@PatchMapping("/{bookingId}")
	public ResponseEntity<Object> changeStatus(@Positive @PathVariable("bookingId") long id,
											   @RequestParam Boolean approved,
											   @RequestHeader("X-Sharer-User-Id") Long userId) {
		log.info("Смена статуса бронирования id: {}, статус approved: {}", id, approved);
		return bookingClient.changeStatus(id, approved, userId);
	}

	@GetMapping("/{bookingId}")
	public ResponseEntity<Object> getBooking(@Positive @RequestHeader("X-Sharer-User-Id") long userId,
											 @Positive @PathVariable Long bookingId) {
		log.info("Получаем бронь id={}, userId={}", bookingId, userId);
		return bookingClient.getBooking(userId, bookingId);
	}

	@GetMapping
	public ResponseEntity<Object> getBookings(@Positive @RequestHeader("X-Sharer-User-Id") long userId,
											  @RequestParam(name = "state", defaultValue = "all") String stateParam,
											  @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
											  @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
		BookingState state = BookingState.from(stateParam)
				.orElseThrow(() -> new IllegalArgumentException("Unknown state: " + stateParam));
		log.info("Получаем брони в state {}, userId={}, from={}, size={}", stateParam, userId, from, size);
		return bookingClient.getBookings(userId, state, from, size);
	}

	@GetMapping("/owner")
	public ResponseEntity<Object> getBookingsOFOwner(@Positive @RequestHeader("X-Sharer-User-Id") Long userId,
													 @RequestParam(name = "state", defaultValue = "all") String stateParam) {
		BookingState state = BookingState.from(stateParam)
				.orElseThrow(() -> new IllegalArgumentException("Unknown state: " + stateParam));
		log.info("Получаем брони владельца в state {}, userId={}", stateParam, userId);
		return bookingClient.getOwnerBookings(userId, state);
	}




}
