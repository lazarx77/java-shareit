package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Comment;

/**
 * Утилитный класс для преобразования объектов комментариев между различными представлениями (DTO).
 * <p>
 * Этот класс содержит статические методы для преобразования сущностей комментариев в DTO
 * и наоборот, упрощая процесс маппинга данных между слоями приложения.
 */
public class CommentMapper {

    /**
     * Преобразует объект Comment в CommentDto.
     *
     * @param comment объект комментария, который нужно преобразовать
     * @return объект CommentDto, содержащий данные из объекта Comment
     *         (идентификатор, имя автора, текст комментария и дату создания)
     */
    public static CommentDto mapToCommentDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setAuthorName(comment.getAuthor().getName());
        dto.setText(comment.getText());
        dto.setCreated(comment.getCreated());
        return dto;
    }
}
