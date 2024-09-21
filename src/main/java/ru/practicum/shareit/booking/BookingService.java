package ru.practicum.shareit.booking;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingAddDto;
import ru.practicum.shareit.booking.dto.State;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

/**
 * Сервис для управления бронированиями.
 * <p>
 * Этот интерфейс определяет методы для выполнения операций с бронированиями,
 * включая создание, изменение статуса и получение информации о бронированиях.
 */
@Transactional(readOnly = true)
public interface BookingService {

    /**
     * Создает новое бронирование.
     *
     * @param bookingDto объект, содержащий данные для создания нового бронирования
     * @param userId идентификатор пользователя, осуществляющего бронирование
     * @return созданное бронирование
     */
    @Transactional
    Booking addBooking(BookingAddDto bookingDto, Long userId);

    /**
     * Изменяет статус существующего бронирования.
     *
     * @param id идентификатор бронирования, статус которого нужно изменить
     * @param approved новый статус бронирования (одобрено/отклонено)
     * @param userId идентификатор пользователя, изменяющего статус
     * @return обновленное бронирование
     */
    @Transactional
    Booking changeStatus(Long id, Boolean approved, Long userId);

    /**
     * Находит конкретное бронирование по его идентификатору.
     *
     * @param id идентификатор бронирования, информацию о котором нужно получить
     * @param userId идентификатор пользователя, запрашивающего информацию
     * @return объект Booking с данными о бронировании
     */
    Booking findSpecificBooking(Long id, Long userId);

    /**
     * Находит все бронирования пользователя.
     *
     * @param userId идентификатор пользователя, для которого нужно получить список бронирований
     * @param state состояние бронирований, которые нужно получить (например, ALL, CURRENT, PAST и т.д.)
     * @return список объектов Booking, соответствующих критериям
     */
    List<Booking> findAllBookingsOfBooker(Long userId, State state);

    /**
     * Находит все бронирования, связанные с предметами, принадлежащими пользователю.
     *
     * @param userId идентификатор владельца предметов
     * @param state состояние бронирований, которые нужно получить (например, ALL, CURRENT, PAST и т.д.)
     * @return список объектов Booking, соответствующих критериям
     */
    List<Booking> findAllBookingsOfOwner(Long userId, State state);

    /**
     * Находит последнее бронирование для указанного предмета.
     *
     * @param item предмет, для которого нужно найти последнее бронирование
     * @return последнее бронирование для указанного предмета
     */
    Booking findLastBooking(Item item);

    /**
     * Находит будущее бронирование для указанного предмета.
     *
     * @param item предмет, для которого нужно найти будущее бронирование
     * @return будущее бронирование для указанного предмета
     */
    Booking findFutureBooking(Item item);
}
