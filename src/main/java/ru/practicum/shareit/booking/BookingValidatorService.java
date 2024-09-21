package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.booking.dto.BookingAddDto;
import ru.practicum.shareit.exception.ValidationException;

import java.time.LocalDateTime;

/**
 * Сервис для валидации данных бронирования.
 * <p>
 * Этот класс содержит статические методы для проверки корректности данных,
 * связанных с бронированиями, таких как идентификаторы и временные рамки.
 */
@Slf4j
public class BookingValidatorService {

    /**
     * Проверяет наличие идентификатора бронирования.
     *
     * @param id идентификатор бронирования, который нужно проверить
     * @throws ValidationException если идентификатор не указан (null)
     */
    public static void validateId(Long id) {
        log.info("Проверка наличия id бронирования: {} ", id);
        if (id == null) {
            throw new ValidationException("Id должен быть указан");
        }
    }

    /**
     * Проверяет корректность временных рамок для нового бронирования.
     *
     * @param dto объект, содержащий данные о бронировании, которые нужно проверить
     * @param now текущее время, используемое для проверки
     * @throws ValidationException если время начала бронирования позже времени окончания
     *                             или если время начала находится в прошлом
     */
    public static void timeCheck(BookingAddDto dto, LocalDateTime now) {
        if (dto.getStart().isBefore(now) && dto.getStart().isAfter(dto.getEnd())) {
            throw new ValidationException("Время начала бронирования должно быть раньше времени окончания, " +
                    "и оба времени не могут быть в прошлом.");
        }
        log.info("Проверка на пересечение времени прошла успешно");
    }
}
