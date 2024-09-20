package ru.practicum.shareit.item.dto;

import lombok.Data;

@Data
public class CommentDto {

    Long id;

    String text;

    Long authorId;
}
