package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.exception.ValidationException;

/**
 * Класс ItemValidatorService предоставляет методы для валидации данных, связанных с предметами.
 * Он используется для проверки корректности идентификаторов и других параметров, чтобы гарантировать,
 * что данные, передаваемые в систему, соответствуют установленным требованиям.
 */
@Slf4j
public class ItemValidatorService {

    /**
     * Проверяет наличие идентификатора предмета.
     *
     * @param id идентификатор предмета, который необходимо проверить.
     * @throws ValidationException если идентификатор не указан (равен null).
     */
    public static void validateId(Long id) {
        log.info("Проверка наличия id вещи: {} ", id);
        if (id == null) {
            throw new ValidationException("Id должен быть указан");
        }
    }
}
