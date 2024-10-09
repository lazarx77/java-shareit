package ru.practicum.shareit.item;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

/**
 * Сервис для управления предметами в системе.
 * Предоставляет методы для выполнения операций с предметами и комментариями.
 */
@Transactional(readOnly = true)
public interface ItemService {

    /**
     * Добавляет новый предмет.
     *
     * @param userId уникальный идентификатор пользователя, добавляющего предмет.
     * @param dto    объект, содержащий данные о предмете.
     * @return добавленный предмет.
     */
    @Transactional
    Item addNewItem(Long userId, ItemDto dto);

    /**
     * Обновляет информацию о существующем предмете.
     *
     * @param userId уникальный идентификатор пользователя, обновляющего предмет.
     * @param itemId уникальный идентификатор предмета, который необходимо обновить.
     * @param dto    объект, содержащий обновленные данные о предмете.
     * @return обновленный предмет.
     */
    @Transactional
    Item updateItem(Long userId, Long itemId, ItemDto dto);

    /**
     * Получает информацию о предмете по его идентификатору.
     *
     * @param id уникальный идентификатор предмета.
     * @return предмет с указанным идентификатором.
     */
    Item getItem(Long id);

    /**
     * Получает все предметы, принадлежащие указанному пользователю.
     *
     * @param userId уникальный идентификатор пользователя.
     * @return список предметов, принадлежащих указанному пользователю.
     */
    List<Item> getAllItemsOfOwner(Long userId);

    /**
     * Ищет предметы по заданному текстовому запросу.
     *
     * @param text текст для поиска предметов.
     * @return список предметов, соответствующих заданному текстовому запросу.
     */
    List<Item> searchItems(String text);

    /**
     * Добавляет комментарий к предмету.
     *
     * @param bookerId уникальный идентификатор пользователя, оставляющего комментарий.
     * @param itemId   уникальный идентификатор предмета, к которому добавляется комментарий.
     * @param dto      объект, содержащий данные комментария.
     * @return добавленный комментарий.
     */
    @Transactional
    Comment addComment(Long bookerId, Long itemId, CommentDto dto);

    /**
     * Получает все комментарии к указанному предмету.
     *
     * @param itemId уникальный идентификатор предмета.
     * @return список комментариев, связанных с указанным предметом.
     */
    List<Comment> getComments(Long itemId);

    /**
     * Получает список предметов, связанных с указанным идентификатором запроса.
     *
     * @param requestId идентификатор запроса, по которому необходимо получить предметы
     * @return список предметов, связанных с указанным запросом
     */
    List<Item> getItemsByRequestId(Long requestId);
}
