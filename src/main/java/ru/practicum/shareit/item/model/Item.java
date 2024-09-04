package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

/**
 * Класс Item представляет собой модель предмета в системе. Он содержит информацию о предмете,
 * включая его идентификатор, название, описание, статус доступности, владельца и запрос на предмет.
 * Этот класс используется для хранения и передачи данных о предметах в приложении.
 *
 * <p>Поля класса:</p>
 * <ul>
 *     <li><b>id</b> - уникальный идентификатор предмета;</li>
 *     <li><b>name</b> - название предмета;</li>
 *     <li><b>description</b> - описание предмета;</li>
 *     <li><b>available</b> - статус доступности предмета (true - доступен, false - недоступен);</li>
 *     <li><b>owner</b> - объект типа {@link User}, представляющий владельца предмета;</li>
 *     <li><b>request</b> - объект типа {@link ItemRequest}, представляющий запрос на предмет (если применимо).</li>
 * </ul>
 *
 * <p>Класс использует аннотации {@link Data}, {@link AllArgsConstructor}, {@link NoArgsConstructor}
 * и {@link EqualsAndHashCode} из библиотеки Lombok для автоматической генерации методов доступа,
 * конструкторов и методов сравнения.</p>
 *
 * <p>Метод {@code isAvailable()} возвращает статус доступности предмета.</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Item {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private User owner;
    private ItemRequest request;

    public Boolean isAvailable() {
        return available;
    }
}
