package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс ItemDto представляет собой Data Transfer Object (DTO) для передачи данных о предмете.
 * Он используется для обмена информацией между слоями приложения, например, между контроллерами
 * и сервисами. Класс содержит поля, которые описывают свойства предмета, а также аннотации
 * для валидации входящих данных.
 *
 * <p>Поля класса:</p>
 * <ul>
 *     <li><b>id</b> - уникальный идентификатор предмета;</li>
 *     <li><b>name</b> - название предмета, не может быть пустым (аннотация {@link NotBlank});</li>
 *     <li><b>description</b> - описание предмета, не может быть пустым (аннотация {@link NotBlank});</li>
 *     <li><b>available</b> - статус доступности предмета, не может быть пустым (аннотация {@link NotNull});</li>
 * </ul>
 *
 * <p>Метод {@code isAvailable()} возвращает статус доступности предмета.</p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDto {
    private Long id;
    @NotBlank(message = "Название не может быть пустым")
    private String name;
    @NotBlank(message = "Описание не может быть пустым")
    private String description;
    @NotNull(message = "Статус доступности не может быть пустым")
    private Boolean available;

    public Boolean isAvailable() {
        return available;
    }
}
