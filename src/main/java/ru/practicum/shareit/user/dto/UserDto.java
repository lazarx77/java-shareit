package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс UserDto представляет собой Data Transfer Object (DTO) для передачи данных о пользователе.
 * Он используется для обмена информацией между слоями приложения, например, между контроллерами
 * и сервисами. Класс содержит поля, которые описывают свойства пользователя.
 *
 * <p>Поля класса:</p>
 * <ul>
 *     <li><b>id</b> - уникальный идентификатор пользователя;</li>
 *     <li><b>name</b> - имя пользователя;</li>
 *     <li><b>email</b> - адрес электронной почты пользователя;</li>
 * </ul>
 *
 * <p>Класс использует аннотации {@link AllArgsConstructor}, {@link NoArgsConstructor} и {@link Data}
 * из библиотеки Lombok для автоматической генерации конструкторов и методов доступа.</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;
    private String name;
    private String email;
}

