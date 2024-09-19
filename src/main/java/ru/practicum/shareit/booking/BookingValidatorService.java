package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.exception.ValidationException;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
public class BookingValidatorService {

    public static void validateId(Long id) {
        log.info("Проверка наличия id бронирования: {} ", id);
        if (id == null) {
            throw new ValidationException("Id должен быть указан");
        }
    }

    public static void isEndDateAfterStartDate(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Проверка на пересечение по времени");
        if (startDate.isBefore(endDate)) {
            throw new ValidationException("Время должно быть указано, дата начала брони должна быть раньше " +
                    "окончания брони");
        }
    }
}
