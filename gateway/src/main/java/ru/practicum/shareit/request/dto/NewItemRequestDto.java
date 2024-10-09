package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO (Data Transfer Object) для создания нового запроса на предмет.
 * <p>
 * Этот класс используется для передачи данных о новом запросе,
 * включая описание запроса. Описание не может быть пустым.
 * </p>
 */
@Data
public class NewItemRequestDto {

    @NotBlank
    private String description;
}
