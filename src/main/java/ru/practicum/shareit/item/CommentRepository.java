package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.Comment;

import java.util.List;

/**
 * Репозиторий для работы с сущностью "Комментарий".
 * Предоставляет методы для выполнения операций с комментариями в базе данных.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Находит все комментарии, связанные с указанным идентификатором предмета.
     *
     * @param itemId уникальный идентификатор предмета, для которого необходимо получить комментарии.
     * @return список комментариев, связанных с указанным предметом.
     */
    List<Comment> findAllByItemId(Long itemId);
}
