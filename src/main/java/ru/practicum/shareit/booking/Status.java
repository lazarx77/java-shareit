package ru.practicum.shareit.booking;

/**
 * Перечисление {@code Status} представляет собой статусы бронирования в системе.
 *
 * <p>Каждый статус указывает текущее состояние бронирования и может принимать одно из следующих значений:</p>
 * <ul>
 *     <li><strong>WAITING</strong> - Бронирование ожидает подтверждения.</li>
 *     <li><strong>APPROVED</strong> - Бронирование подтверждено.</li>
 *     <li><strong>REJECTED</strong> - Бронирование отклонено.</li>
 *     <li><strong>CANCELED</strong> - Бронирование отменено.</li>
 * </ul>
 */
public enum Status {
    WAITING,
    APPROVED,
    REJECTED,
    CANCELED
}
