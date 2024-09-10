package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Класс ItemRequest представляет собой модель запроса на предмет в системе.
 * Он содержит информацию о запросе, включая его идентификатор, описание,
 * связанные запросы и дату создания. Этот класс используется для хранения
 * и передачи данных о запросах на предметы в приложении.
 *
 * <p>Поля класса:</p>
 * <ul>
 *     <li><b>id</b> - уникальный идентификатор запроса;</li>
 *     <li><b>description</b> - описание запроса;</li>
 *     <li><b>request</b> - объект типа {@link ItemRequest}, представляющий связанный запрос (если применимо);</li>
 *     <li><b>created</b> - дата и время создания запроса.</li>
 * </ul>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemRequest {
    long id;
    String description;
    ItemRequest request;
    LocalDateTime created;
}
