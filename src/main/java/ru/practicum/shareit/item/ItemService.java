package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemOwnerDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

/**
 * Интерфейс ItemService определяет контракт для работы с предметами в системе.
 * Он предоставляет методы для добавления, обновления, получения и поиска предметов.
 * Реализации этого интерфейса могут использовать различные способы обработки данных,
 * такие как взаимодействие с репозиториями и бизнес-логика.
 */
public interface ItemService {

    /**
     * Добавляет новый предмет в систему.
     *
     * @param userId идентификатор пользователя, добавляющего предмет.
     * @param dto    объект типа {@link ItemDto}, содержащий данные о предмете.
     * @return добавленный объект типа {@link Item}.
     */
    Item addNewItem(Long userId, ItemDto dto);

    /**
     * Обновляет существующий предмет в системе.
     *
     * @param userId идентификатор пользователя, пытающегося обновить предмет.
     * @param itemId идентификатор предмета, который необходимо обновить.
     * @param dto    объект типа {@link ItemDto}, содержащий новые данные о предмете.
     * @return обновленный объект типа {@link Item}.
     */
    Item updateItem(Long userId, Long itemId, ItemDto dto);

    /**
     * Получает объект типа {@link ItemDto} по его идентификатору.
     *
     * @param id идентификатор предмета, который необходимо получить.
     * @return объект типа {@link ItemDto}, содержащий данные о предмете.
     */
    Item getItem(Long id);

    /**
     * Получает все предметы, принадлежащие указанному владельцу.
     *
     * @param userId идентификатор владельца, чьи предметы необходимо получить.
     * @return список объектов типа {@link ItemOwnerDto}, содержащих данные о предметах владельца.
     */
    List<Item> getAllItemsOfOwner(Long userId);

    /**
     * Ищет предметы по текстовому запросу.
     *
     * @param text текст для поиска предметов.
     * @return список объектов типа {@link ItemDto}, соответствующих критериям поиска.
     */
    List<Item> searchItems(String text);

    Comment addComment(Long bookerId, Long itemId, CommentDto dto);

    List<Comment> getComments(Long itemId);
}
