package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.request.dto.NewItemRequestDto;

/**
 * Клиент для взаимодействия с API запросов на предметы.
 * <p>
 * Этот сервис предоставляет методы для выполнения операций
 * с запросами на предметы, таких как добавление нового запроса,
 * получение запросов пользователя и получение запросов других пользователей.
 * </p>
 */
@Service
public class RequestClient extends BaseClient {

    private static final String API_PREFIX = "/requests";

    @Autowired
    public RequestClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    /**
     * Добавляет новый запрос на предмет для указанного пользователя.
     *
     * @param requestorId идентификатор пользователя, создающего запрос
     * @param dto         DTO с данными о новом запросе
     * @return ResponseEntity с результатом операции
     */
    public ResponseEntity<Object> addItemRequest(long requestorId, NewItemRequestDto dto) {
        return post("", requestorId, dto);
    }

    /**
     * Получает список запросов, созданных указанным пользователем.
     *
     * @param requestorId идентификатор пользователя
     * @return ResponseEntity с результатом операции
     */
    public ResponseEntity<Object> getRequestsOfRequestor(long requestorId) {
        return get("", requestorId);
    }

    /**
     * Получает все запросы, созданные другими пользователями.
     *
     * @param userId идентификатор пользователя
     * @return ResponseEntity с результатом операции
     */
    public ResponseEntity<Object> getAllRequestsOfOtherUsers(long userId) {
        return get("/all", userId);
    }

    /**
     * Получает информацию о запросе по его идентификатору.
     *
     * @param userId    идентификатор пользователя
     * @param requestId идентификатор запроса
     * @return ResponseEntity с результатом операции
     */
    public ResponseEntity<Object> getRequestById(long userId, long requestId) {
        return get("/" + requestId, userId);
    }
}
