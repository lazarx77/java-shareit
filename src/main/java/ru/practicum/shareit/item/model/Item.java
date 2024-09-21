package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
 * <p>Метод {@code isAvailable()} возвращает статус доступности предмета.</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название не может быть пустым")
    @NotNull(message = "Название не может быть Null")
    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(name = "is_available", nullable = false)
    private Boolean available;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private ItemRequest request;

    public Boolean isAvailable() {
        return available;
    }
}
