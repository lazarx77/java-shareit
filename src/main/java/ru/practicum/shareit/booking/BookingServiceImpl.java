package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.dto.State;
import ru.practicum.shareit.exception.ItemDoNotBelongToUser;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.UserValidatorService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    BookingRepository bookingRepository;
    UserService userService;
    ItemService itemService;

    public Booking addBooking(BookingDto dto, Long userId) {
        log.info("Запуск записи бронирования");
        UserValidatorService.validateId(userId);
        dto.setBooker(userService.findUserById(userId));
        Booking booking = BookingMapper.mapToBooking(dto);
        booking.setStatus(Status.WAITING);
        return bookingRepository.save(booking);
    }

    public Booking changeStatus(Long id, Boolean approved, Long userId) {
        log.info("Смена статуса бронирования id= " + id);
        BookingValidatorService.validateId(id);
        UserValidatorService.validateId(userId);
        log.info("Проверка наличия статуса approved в запросе");
        if (approved == null) {
            throw new ValidationException("Поле approved не должно быть пустым, либо должно быть true или false");
        }
        log.info("Проверка наличия бронирования с id= " + id);
        Booking booking = bookingRepository
                .findById(id).orElseThrow(() -> new NotFoundException("Бронировение с id " + id + "не существует"));
        log.info("Проверка владельца бронируемой вещи");
        if (!booking.getItem().getOwner().getId().equals(userId)) {
            throw new ItemDoNotBelongToUser("Пользователь с id " + userId + "не является владельнем бронируемой вещи");
        }
        booking.setStatus(approved ? Status.APPROVED : Status.REJECTED);
        return bookingRepository.save(booking);
    }

    public Booking findSpecificBooking(Long id, Long userId) {
        BookingValidatorService.validateId(id);
        UserValidatorService.validateId(userId);
        return bookingRepository.findByBooker_idOrItemOwner_id(id, userId)
                .orElseThrow(() -> new NotFoundException("Такое бронирование не существует"));
    }

    public List<Booking> findAllBookingsOfBooker(Long userId, State state) {
        UserValidatorService.validateId(userId);
        return switch (state) {
            case ALL -> bookingRepository.findAllBookingsByBooker_idOrderByStartDateDesc(userId);
            case CURRENT -> bookingRepository
                    .findAllByBooker_IdAndStartDateBeforeAndEndDateAfterOrderByStartDateDesc(userId,
                            LocalDateTime.now(), LocalDateTime.now());
            case PAST -> bookingRepository
                    .findAllByBooker_idAndEndDateAfterOrderByStartDateDesc(userId, LocalDateTime.now());
            case FUTURE -> bookingRepository
                    .findAllByBooker_idAndStartDateAfterOrderByStartDateDesc(userId, LocalDateTime.now());
            case WAITING ->
                    bookingRepository.findAllByBooker_idAndStatusOrderByStartDateDesc(userId, Status.WAITING);
            case REJECTED ->
                    bookingRepository.findAllByBooker_idAndStatusOrderByStartDateDesc(userId, Status.REJECTED);
            default -> throw new IllegalArgumentException("Unknown state: " + state);
        };
    }

    public List<Booking> findAllBookingsOfOwner(Long userId, State state) {
        UserValidatorService.validateId(userId);
        return switch (state) {
            case ALL -> bookingRepository.findAllByItem_Owner_idOrderByStartDateDesc(userId);
            case CURRENT -> bookingRepository
                    .findAllByItem_Owner_idAndStartDateBeforeAndEndDateAfterOrderByStartDateDesc(userId,
                            LocalDateTime.now(), LocalDateTime.now());
            case PAST -> bookingRepository
                    .findAllByItem_Owner_idAndEndDateAfterOrderByStartDateDesc(userId, LocalDateTime.now());
            case FUTURE -> bookingRepository
                    .findAllByItem_Owner_idAndStartDateAfterOrderByStartDateDesc(userId, LocalDateTime.now());
            case WAITING -> bookingRepository
                    .findAllByItem_Owner_idAndStatusOrderByStartDateDesc(userId, Status.WAITING);
            case REJECTED -> bookingRepository
                    .findAllByItem_Owner_idAndStatusOrderByStartDateDesc(userId, Status.REJECTED);
            default -> throw new IllegalArgumentException("Unknown state: " + state);
        };
    }

    public Set<LocalDateTime> lastDates(Long itemId) {
        Booking lastbooking = bookingRepository
                .findFirstByItem_Owner_idAndStartDateBeforeOrderByStartDateDesc(itemService
                        .getItem(itemId)
                        .getOwner()
                        .getId(), LocalDateTime.now())
                .orElseThrow(() -> new NotFoundException("Такой брони нет"));
        Set<LocalDateTime> lastTimeSet = new HashSet<>();
        lastTimeSet.add(lastbooking.getStartDate());
        lastTimeSet.add(lastbooking.getEndDate());
        return lastTimeSet;
    }

    public Set<LocalDateTime> futureDates(Long itemId) {
        Booking futureBooking = bookingRepository
                .findFirstByItem_Owner_idAndStartDateAfterOrderByStartDateAsc(itemService
                        .getItem(itemId).getOwner()
                        .getId(), LocalDateTime.now()).orElseThrow(() -> new NotFoundException("Такой брони нет"));
        Set<LocalDateTime> futureTimeSet = new HashSet<>();
        futureTimeSet.add(futureBooking.getStartDate());
        futureTimeSet.add(futureBooking.getEndDate());
        return futureTimeSet;
    }
}
