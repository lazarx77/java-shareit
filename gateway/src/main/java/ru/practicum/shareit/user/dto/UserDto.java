package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс UserDto представляет собой Data Transfer Object (DTO) для передачи данных о пользователе.
 * Он используется для обмена информацией между слоями приложения, например, между контроллерами
 * и сервисами. Класс содержит поля, которые описывают свойства пользователя.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
}

