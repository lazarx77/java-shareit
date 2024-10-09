package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO (Data Transfer Object) для представления нового запроса на предмет.
 * Содержит информацию о запросе, включая его описание.
 * Поле description должно быть непустым.
 */
@Data
public class NewItemRequestDto {

    @NotBlank
    private String description;
}
