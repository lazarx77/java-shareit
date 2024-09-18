package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс ItemRepository определяет контракт для работы с предметами в системе.
 * Он предоставляет методы для добавления, обновления, поиска и получения предметов.
 * Реализации этого интерфейса могут использовать различные способы хранения данных,
 * такие как базы данных, хранилища в памяти и т.д.
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

//    /**
//     * Добавляет новый предмет в хранилище.
//     *
//     * @param userId идентификатор пользователя, добавляющего предмет.
//     * @param dto    объект типа {@link ItemDto}, содержащий данные о предмете.
//     * @return добавленный объект типа {@link Item}.
//     */
//    Item addItem(Long userId, ItemDto dto);
//
//    /**
//     * Обновляет существующий предмет в хранилище.
//     *
//     * @param userId идентификатор пользователя, пытающегося обновить предмет.
//     * @param itemId идентификатор предмета, который необходимо обновить.
//     * @param dto    объект типа {@link ItemDto}, содержащий новые данные о предмете.
//     * @return обновленный объект типа {@link Item}.
//     */
//    Item updateItem(Long userId, Long itemId, ItemDto dto);
//
//    /**
//     * Находит предмет по его идентификатору.
//     *
//     * @param itemId идентификатор предмета, который необходимо найти.
//     * @return объект типа {@link Optional<Item>}, содержащий найденный предмет или пустой объект, если предмет не найден.
//     */
//    Optional<Item> findByItemId(Long itemId);

    /**
     * Находит все предметы, принадлежащие указанному владельцу.
     *
     * @param ownerId идентификатор владельца, чьи предметы необходимо найти.
     * @return список предметов, принадлежащих указанному владельцу.
     */
    List<Item> findByOwnerId(Long ownerId);

//    /**
//     * Возвращает список всех предметов в хранилище.
//     *
//     * @return список всех предметов.
//     */
//    List<Item> findAllItems();

    @Query(" select i from Item i " +
            "where upper(i.name) like upper(concat('%', ?1, '%')) " +
            " or upper(i.description) like upper(concat('%', ?1, '%'))")
    List<Item> search(String text);
}
