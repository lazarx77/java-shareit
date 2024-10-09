package ru.practicum.shareit.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

/**
 * Репозиторий для работы с сущностями запросов на предметы.
 * Расширяет JpaRepository для предоставления стандартных операций CRUD
 * и включает дополнительные методы для работы с запросами.
 */
public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {


    /**
     * Находит все запросы, созданные пользователем с указанным идентификатором,
     * и сортирует их по дате создания в порядке убывания.
     *
     * @param requestorId идентификатор пользователя, чьи запросы необходимо найти
     * @return список запросов, созданных указанным пользователем
     */
    List<ItemRequest> findByRequestorIdOrderByCreatedDesc(Long requestorId);

    /**
     * Находит все запросы, созданные другими пользователями, исключая запросы
     * от пользователя с указанным идентификатором, и сортирует их по дате создания
     * в порядке убывания.
     *
     * @param userId идентификатор пользователя, запросы которого необходимо исключить
     * @return список запросов, созданных другими пользователями
     */
    @Query("select ir from ItemRequest ir where ir.requestor.id <> :userId order by ir.created desc")
    List<ItemRequest> findAllExcludedByUserIdDesc(@Param("userId") Long userId);
}
