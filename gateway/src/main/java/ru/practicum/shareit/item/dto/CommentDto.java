package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) для представления комментария.
 * <p>
 * Этот класс используется для передачи данных о комментариях, включая
 * идентификатор, текст комментария, имя автора и дату создания .
 */
@Data
public class CommentDto {

    @NotBlank
    private String text;
}
