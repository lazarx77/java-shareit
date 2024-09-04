package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс ItemRepository определяет контракт для работы с предметами в системе.
 * Он предоставляет методы для добавления, обновления, поиска и получения предметов.
 * Реализации этого интерфейса могут использовать различные способы хранения данных,
 * такие как базы данных, хранилища в памяти и т.д.
 *
 * <p>Методы интерфейса:</p>
 * <ul>
 *     <li>
 *         {@link #addNewItem(Long, ItemDto)} - добавляет новый предмет в хранилище.
 *     </li>
 *     <li>
 *         {@link #updateItem(Long, Long, ItemDto)} - обновляет существующий предмет.
 *     </li>
 *     <li>
 *         {@link #findById(Long)} - находит предмет по его идентификатору.
 *     </li>
 *     <li>
 *         {@link #findByOwnerId(Long)} - находит все предметы, принадлежащие указанному владельцу.
 *     </li>
 *     <li>
 *         {@link #findAll()} - возвращает список всех предметов в хранилище.
 *     </li>
 * </ul>
 */
public interface ItemRepository {

    /**
     * Добавляет новый предмет в хранилище.
     *
     * @param userId идентификатор пользователя, добавляющего предмет.
     * @param dto    объект типа {@link ItemDto}, содержащий данные о предмете.
     * @return добавленный объект типа {@link Item}.
     */
    Item addNewItem(Long userId, ItemDto dto);

    /**
     * Обновляет существующий предмет в хранилище.
     *
     * @param userId идентификатор пользователя, пытающегося обновить предмет.
     * @param itemId идентификатор предмета, который необходимо обновить.
     * @param dto    объект типа {@link ItemDto}, содержащий новые данные о предмете.
     * @return обновленный объект типа {@link Item}.
     */
    Item updateItem(Long userId, Long itemId, ItemDto dto);

    /**
     * Находит предмет по его идентификатору.
     *
     * @param itemId идентификатор предмета, который необходимо найти.
     * @return объект типа {@link Optional<Item>}, содержащий найденный предмет или пустой объект, если предмет не найден.
     */
    Optional<Item> findById(Long itemId);

    /**
     * Находит все предметы, принадлежащие указанному владельцу.
     *
     * @param ownerId идентификатор владельца, чьи предметы необходимо найти.
     * @return список предметов, принадлежащих указанному владельцу.
     */
    List<Item> findByOwnerId(Long ownerId);

    /**
     * Возвращает список всех предметов в хранилище.
     *
     * @return список всех предметов.
     */
    List<Item> findAll();
}
