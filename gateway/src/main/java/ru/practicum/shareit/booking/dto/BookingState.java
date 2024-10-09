package ru.practicum.shareit.booking.dto;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Перечисление, представляющее различные состояния бронирования.
 * <p>
 * Доступные состояния:
 * <ul>
 *     <li>ALL - Все состояния бронирования.</li>
 *     <li>CURRENT - Текущие бронирования.</li>
 *     <li>FUTURE - Будущие бронирования.</li>
 *     <li>PAST - Прошедшие бронирования.</li>
 *     <li>REJECTED - Отклоненные бронирования.</li>
 *     <li>WAITING - Ожидающие подтверждения бронирования.</li>
 * </ul>
 * </p>
 */
public enum BookingState {
    ALL,
    CURRENT,
    FUTURE,
    PAST,
    REJECTED,
    WAITING;

    /**
     * Преобразует строковое представление состояния бронирования в соответствующий объект {@link BookingState}.
     *
     * @param stringState строковое представление состояния бронирования
     * @return {@link Optional} содержащий соответствующее состояние {@link BookingState}, если оно существует,
     * или {@link Optional#empty()} если состояние не найдено.
     */
    public static Optional<BookingState> from(String stringState) {
        return Stream.of(values())
                .filter(state -> state.name().equalsIgnoreCase(stringState))
                .findFirst();
    }
}
