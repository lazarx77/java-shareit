package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.booking.dto.BookingAddDto;
import ru.practicum.shareit.exception.ValidationException;

import java.time.LocalDateTime;

@Slf4j
public class BookingValidatorService {

    public static void validateId(Long id) {
        log.info("Проверка наличия id бронирования: {} ", id);
        if (id == null) {
            throw new ValidationException("Id должен быть указан");
        }
    }

    public static void timeCheck(BookingAddDto dto, LocalDateTime now) {
        log.info("Проверка на пересечение времени");
        if (!(dto.getStart().isBefore(dto.getEnd()) && (dto.getStart()).isAfter(now))        ) {
            throw new ValidationException("Время начала бронирования должно быть раньше времени окончания, " +
                    "и оба времени не могут быть в прошлом.");
        }
    }
}
