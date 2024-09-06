package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 * Класс {@code Booking} представляет собой бронирование предмета в системе.
 *
 * <p>Каждое бронирование содержит информацию о времени начала и окончания бронирования,
 * а также о предмете, который бронируется, пользователе, который осуществляет бронирование,
 * и статусе бронирования.</p>
 *
 * <p>Поля класса:</p>
 * <ul>
 *     <li><strong>id</strong> - уникальный идентификатор бронирования.</li>
 *     <li><strong>start</strong> - дата и время начала бронирования.</li>
 *     <li><strong>end</strong> - дата и время окончания бронирования.</li>
 *     <li><strong>item</strong> - объект {@link Item}, представляющий предмет, который бронируется.</li>
 *     <li><strong>booker</strong> - объект {@link User}, представляющий пользователя, который осуществляет
 *     бронирование.</li>
 *     <li><strong>status</strong> - статус бронирования, представленный перечислением {@link Status}.</li>
 * </ul>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Booking {
    private long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Item item;
    private User booker;
    private Status status;
}
