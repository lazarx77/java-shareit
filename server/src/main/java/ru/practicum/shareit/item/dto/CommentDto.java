package ru.practicum.shareit.item.dto;

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

    private Long id;

    private String text;

    private String authorName;

    private LocalDateTime created;
}
