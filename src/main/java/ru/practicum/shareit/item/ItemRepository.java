package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

/**
 * Репозиторий для работы с сущностью "Предмет".
 * Предоставляет методы для выполнения операций с предметами в базе данных.
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

    /**
     * Находит все предметы, принадлежащие указанному владельцу.
     *
     * @param ownerId уникальный идентификатор владельца предметов.
     * @return список предметов, принадлежащих указанному владельцу.
     */
    List<Item> findByOwnerId(Long ownerId);

    /**
     * Ищет предметы по заданному текстовому запросу.
     * Поиск осуществляется по названию и описанию предметов, игнорируя регистр.
     *
     * @param text текст для поиска предметов.
     * @return список предметов, соответствующих заданному текстовому запросу.
     */
    @Query(" select i from Item i " +
            "where upper(i.name) like upper(concat('%', ?1, '%')) " +
            " or upper(i.description) like upper(concat('%', ?1, '%'))")
    List<Item> search(String text);

    @Query("SELECT i FROM Item i WHERE i.request.id = ?1")
    List<Item> findAllByRequestId(Long requestId);
}
