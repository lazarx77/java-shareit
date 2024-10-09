package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.model.User;

import java.util.List;


/**
 * Реализация сервиса для управления запросами на предметы.
 * Содержит методы для создания и получения запросов,
 * а также для работы с запросами других пользователей.
 */
@Service
@AllArgsConstructor
@Slf4j
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepository itemRequestRepository;
    private final UserService userService;

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemRequest addNewRequest(Long requestorId, ItemRequest itemRequest) {
        log.info("Проверка наличия пользователя с id = {} в БД при добавлении запроса", requestorId);
        User requestor = userService.findUserById(requestorId);
        itemRequest.setRequestor(requestor);
        log.info("Попытка записи запроса в БД при добавлении запроса на предмет");
        return itemRequestRepository.save(itemRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ItemRequest> getRequestsOfRequestor(Long requestorId) {
        log.info("Проверка наличия пользователя при поиске его запросов с id = {} в БД", requestorId);
        userService.findUserById(requestorId);
        return itemRequestRepository.findByRequestorIdOrderByCreatedDesc(requestorId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ItemRequest> getAllRequestsOfOtherUsers(Long userId) {
        log.info("Проверка наличия пользователя при поиске чужих запросов с id = {} в БД", userId);
        userService.findUserById(userId);
        return itemRequestRepository.findAllExcludedByUserIdDesc(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemRequest getRequestById(Long userId, Long requestId) {
        log.info("Проверка наличия пользователя при поиске конкретного запроса с id = {} в БД", userId);
        userService.findUserById(userId);
        log.info("Пользователь userId={}", userId);
        log.info("Запрос requestId={}", requestId);
        return itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Такой запрос с id = " + requestId + " не найден "));
    }
}
