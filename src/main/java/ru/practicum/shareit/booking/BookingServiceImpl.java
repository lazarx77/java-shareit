package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingAddDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.dto.State;
import ru.practicum.shareit.exception.ItemDoNotBelongToUser;
import ru.practicum.shareit.exception.NotAvailableException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.UserValidatorService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    BookingRepository bookingRepository;
    UserService userService;
    ItemService itemService;

    @Override
    public Booking addBooking(BookingAddDto dto, Long userId) {
        BookingValidatorService.timeCheck(dto, LocalDateTime.now());
        log.info("Запуск записи бронирования");
        UserValidatorService.validateId(userId);
        log.info("Проверка наличия пользователя в БД");
        userService.findUserById(userId);
        Item item = itemService.getItem(dto.getItemId());
        if (!item.isAvailable()) {
            throw new NotAvailableException("Вещь с id= " + item.getId() + " недоступна для бронирования");
        }
        dto.setBooker(userService.findUserById(userId));
        Booking booking = BookingMapper.mapToBookingFromAddDto(dto, itemService);
        booking.setStatus(Status.WAITING);
        return bookingRepository.save(booking);
    }

    @Override
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
                .findById(id).orElseThrow(() -> new NotFoundException("Бронировение с id " + id + " не существует"));
        log.info("Проверка владельца бронируемой вещи");
        if (!booking.getItem().getOwner().getId()
                .equals(userId)) {
            throw new ItemDoNotBelongToUser("Пользователь с id " + userId + " не является владельнем бронируемой вещи");
        }
        booking.setStatus(approved ? Status.APPROVED : Status.REJECTED);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking findSpecificBooking(Long id, Long userId) {
        BookingValidatorService.validateId(id);
        UserValidatorService.validateId(userId);
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Такая бронь не найдена"));
        if (booking.getBooker().getId().equals(userId) || booking.getItem().getOwner().getId().equals(userId)) {
            return booking;
        } else {
            throw new ItemDoNotBelongToUser("Такой брони не существует для данного пользователя");
        }
    }

    @Override
    public List<Booking> findAllBookingsOfBooker(Long userId, State state) {
        UserValidatorService.validateId(userId);
        log.info("Проверка наличия пользователя в БД при получении списка всех бронирований");
        userService.findUserById(userId);
        log.info("STATE " + state);
        List<Booking> bookingList;
        switch (state) {
            case ALL -> bookingList = bookingRepository.findAllBookingsByBooker_idOrderByStartDesc(userId);
            case CURRENT -> bookingList = bookingRepository
                    .findAllByBooker_idAndStartBeforeAndEndAfterOrderByStartDesc(userId,
                            LocalDateTime.now(), LocalDateTime.now());
            case PAST -> bookingList = bookingRepository
                    .findAllByBooker_idAndEndAfterOrderByStartDesc(userId, LocalDateTime.now());
            case FUTURE -> bookingList = bookingRepository
                    .findAllByBooker_idAndStartAfterOrderByStartDesc(userId, LocalDateTime.now());
            case WAITING ->
                    bookingList = bookingRepository.findAllByBooker_idAndStatusOrderByStartDesc(userId, Status.WAITING);
            case REJECTED ->
                    bookingList = bookingRepository.findAllByBooker_idAndStatusOrderByStartDesc(userId, Status.REJECTED);
            default -> throw new IllegalArgumentException("Неизвестное состояние: " + state);
        }
        if (bookingList.isEmpty()) {
            throw new ItemDoNotBelongToUser("Брони не существует для данного пользователя");
        }
        return bookingList;
    }

    @Override
    public List<Booking> findAllBookingsOfOwner(Long userId, State state) {
        UserValidatorService.validateId(userId);
        List<Booking> bookingList;
        switch (state) {
            case ALL -> bookingList = bookingRepository.findAllByItem_Owner_idOrderByStartDesc(userId);
            case CURRENT -> bookingList = bookingRepository
                    .findAllByItem_Owner_idAndStartBeforeAndEndAfterOrderByStartDesc(userId,
                            LocalDateTime.now(), LocalDateTime.now());
            case PAST -> bookingList = bookingRepository
                    .findAllByItem_Owner_idAndEndAfterOrderByStartDesc(userId, LocalDateTime.now());
            case FUTURE -> bookingList = bookingRepository
                    .findAllByItem_Owner_idAndStartAfterOrderByStartDesc(userId, LocalDateTime.now());
            case WAITING -> bookingList = bookingRepository
                    .findAllByItem_Owner_idAndStatusOrderByStartDesc(userId, Status.WAITING);
            case REJECTED -> bookingList = bookingRepository
                    .findAllByItem_Owner_idAndStatusOrderByStartDesc(userId, Status.REJECTED);
            default -> throw new IllegalArgumentException("Неизвестное состояние: " + state);
        }
        if (bookingList.isEmpty()) {
            throw new ItemDoNotBelongToUser("Брони не существует для данного пользователя");
        }
        return bookingList;
    }

    @Override
    public Booking findLastBooking(Item item) {
        return bookingRepository
                .findFirstByItem_Owner_idAndStartBeforeOrderByStartDesc(itemService
                        .getItem(item.getId())
                        .getOwner()
                        .getId(), LocalDateTime.now())
                .orElse(null);
    }

    @Override
    public Booking findFutureBooking(Item item) {
        return bookingRepository
                .findFirstByItem_Owner_idAndStartAfterOrderByStartAsc(itemService
                        .getItem(item.getId()).getOwner()
                        .getId(), LocalDateTime.now()).orElse(null);
    }
}
