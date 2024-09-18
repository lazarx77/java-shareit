package ru.practicum.shareit.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Класс User представляет собой модель пользователя в системе.
 * Он содержит информацию о пользователе, включая его имя и электронную почту.
 *
 * <p>Класс использует аннотации валидации для обеспечения корректности данных,
 * а также аннотации Lombok для автоматической генерации методов доступа,
 * конструкторов и методов сравнения.</p>
 *
 * <p>Два объекта User считаются равными, если у них
 * одинаковые значения поля email.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "email")
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя не может быть пустым")
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty(message = "Электронная почта не может быть пустой")
    @Email(message = "Электронная почта должна соответствовать формату электронной почты")
    @Column(name = "email", unique = true, nullable = false)
    private String email;
}
