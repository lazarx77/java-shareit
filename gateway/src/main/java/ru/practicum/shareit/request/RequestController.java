package ru.practicum.shareit.request;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler;
import ru.practicum.shareit.request.dto.NewItemRequestDto;

@Controller
@Slf4j
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> addItemRequest(@Positive @RequestHeader("X-Sharer-User-Id") long requestorId,
                                                 @Validated @RequestBody NewItemRequestDto dto) {
        log.info("Передача запроса предмета пользователем requestirId={}", requestorId);
        return requestClient.addItemRequest(requestorId, dto);
    }

    @GetMapping
    public ResponseEntity<Object> getRequestsOfRequestor(@Positive
                                                             @RequestHeader("X-Sharer-User-Id") long requestorId) {
        log.info("Получение запросов пользователя requestirId={}", requestorId);
        return requestClient.getRequestsOfRequestor(requestorId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequestsOfOtherUsers(@Positive @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Получение чужих запросов пользователем userId={}", userId);
        return requestClient.getAllRequestsOfOtherUsers(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequestById(@Positive @RequestHeader("X-Sharer-User-Id") long userId,
                                                 @Positive @PathVariable("requestId") long requestId) {
        log.info("Получение запроса по id={} пользователем userId={}", requestId, userId);
        return requestClient.getRequestById(userId, requestId);
    }
}
