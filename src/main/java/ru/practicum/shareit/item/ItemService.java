package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemOwnerDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

/**
 * Интерфейс ItemService определяет контракт для работы с предметами в системе.
 * Он предоставляет методы для добавления, обновления, получения и поиска предметов.
 * Реализации этого интерфейса могут использовать различные способы обработки данных,
 * такие как взаимодействие с репозиториями и бизнес-логика.
 *
 * <p>Методы интерфейса:</p>
 * <ul>
 *     <li>
 *         {@link #addNewItem(Long, ItemDto)} - добавляет новый предмет в систему.
 *     </li>
 *     <li>
 *         {@link #updateItem(Long, Long, ItemDto)} - обновляет существующий предмет.
 *     </li>
 *     <li>
 *         {@link #getItemDtoById(Long)} - получает объект типа {@link ItemDto} по его идентификатору.
 *     </li>
 *     <li>
 *         {@link #getAllItemsOfOwner(Long)} - получает все предметы, принадлежащие указанному владельцу.
 *     </li>
 *     <li>
 *         {@link #searchItems(String)} - ищет предметы по текстовому запросу.
 *     </li>
 * </ul>
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
    ItemDto getItemDtoById(Long id);

    /**
     * Получает все предметы, принадлежащие указанному владельцу.
     *
     * @param userId идентификатор владельца, чьи предметы необходимо получить.
     * @return список объектов типа {@link ItemOwnerDto}, содержащих данные о предметах владельца.
     */
    List<ItemOwnerDto> getAllItemsOfOwner(Long userId);

    /**
     * Ищет предметы по текстовому запросу.
     *
     * @param text текст для поиска предметов.
     * @return список объектов типа {@link ItemDto}, соответствующих критериям поиска.
     */
    List<ItemDto> searchItems(String text);
}