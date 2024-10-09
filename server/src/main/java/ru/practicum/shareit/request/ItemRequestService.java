package ru.practicum.shareit.request;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

/**
 * Сервис для управления запросами на предметы.
 * Определяет методы для создания и получения запросов,
 * а также для работы с запросами других пользователей.
 */
@Transactional(readOnly = true)
public interface ItemRequestService {

    /**
     * Создает новый запрос на предмет.
     *
     * @param requestorId идентификатор пользователя, создающего запрос
     * @param itemRequest объект ItemRequest, содержащий данные нового запроса
     * @return созданный объект ItemRequest
     */
    @Transactional
    ItemRequest addNewRequest(Long requestorId, ItemRequest itemRequest);

    /**
     * Получает все запросы текущего пользователя.
     *
     * @param requestorId идентификатор пользователя
     * @return список запросов текущего пользователя
     */
    List<ItemRequest> getRequestsOfRequestor(Long requestorId);

    /**
     * Получает все запросы на предметы от других пользователей.
     *
     * @param userId идентификатор пользователя
     * @return список запросов от других пользователей
     */
    List<ItemRequest> getAllRequestsOfOtherUsers(Long userId);

    /**
     * Получает запрос на предмет по его идентификатору.
     *
     * @param userId    идентификатор пользователя
     * @param requestId идентификатор запроса
     * @return объект ItemRequest, представляющий запрашиваемый запрос
     */
    ItemRequest getRequestById(Long userId, Long requestId);
}
