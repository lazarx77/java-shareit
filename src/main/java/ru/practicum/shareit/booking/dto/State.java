package ru.practicum.shareit.booking.dto;

/**
 * Перечисление, представляющее возможные состояния бронирования.
 * <p>
 * Это перечисление используется для обозначения различных статусов бронирования
 */
public enum State {
    ALL,
    CURRENT,
    PAST,
    FUTURE,
    WAITING,
    REJECTED
}
