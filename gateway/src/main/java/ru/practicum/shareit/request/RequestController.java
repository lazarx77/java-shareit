package ru.practicum.shareit.request;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.NewItemRequestDto;

/**
 * Контроллер для обработки запросов, связанных с предметами.
 * Обеспечивает функциональность для добавления и получения запросов на предметы.
 */
@Controller
@Slf4j
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestClient requestClient;

    /**
     * Добавляет новый запрос на предмет.
     *
     * @param requestorId идентификатор пользователя, создающего запрос
     * @param dto         объект, содержащий данные нового запроса на предмет
     * @return ResponseEntity с результатом операции
     */
    @PostMapping
    public ResponseEntity<Object> addItemRequest(@Positive @RequestHeader("X-Sharer-User-Id") long requestorId,
                                                 @Validated @RequestBody NewItemRequestDto dto) {
        log.info("Передача запроса предмета пользователем requestirId={}", requestorId);
        return requestClient.addItemRequest(requestorId, dto);
    }

    /**
     * Получает все запросы, созданные пользователем.
     *
     * @param requestorId идентификатор пользователя, чьи запросы нужно получить
     * @return ResponseEntity с списком запросов пользователя
     */
    @GetMapping
    public ResponseEntity<Object> getRequestsOfRequestor(@Positive
                                                         @RequestHeader("X-Sharer-User-Id") long requestorId) {
        log.info("Получение запросов пользователя requestirId={}", requestorId);
        return requestClient.getRequestsOfRequestor(requestorId);
    }

    /**
     * Получает все запросы от других пользователей.
     *
     * @param userId идентификатор пользователя, запрашивающего данные
     * @return ResponseEntity со списком запросов от других пользователей
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequestsOfOtherUsers(@Positive @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Получение чужих запросов пользователем userId={}", userId);
        return requestClient.getAllRequestsOfOtherUsers(userId);
    }

    /**
     * Получает запрос по его идентификатору.
     *
     * @param userId    идентификатор пользователя, запрашивающего данные
     * @param requestId идентификатор запроса, который нужно получить
     * @return ResponseEntity с данными запрашиваемого запроса
     */
    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequestById(@Positive @RequestHeader("X-Sharer-User-Id") long userId,
                                                 @Positive @PathVariable("requestId") long requestId) {
        log.info("Получение запроса по id={} пользователем userId={}", requestId, userId);
        return requestClient.getRequestById(userId, requestId);
    }
}
