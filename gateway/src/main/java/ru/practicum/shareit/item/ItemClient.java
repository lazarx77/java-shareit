package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Map;

/**
 * Клиент для взаимодействия с API предметов.
 * <p>
 * Этот сервис предоставляет методы для выполнения операций
 * с предметами, таких как добавление, обновление, получение
 * информации о предмете, получение предметов владельца и
 * поиск предметов.
 * </p>
 */
@Service
public class ItemClient extends BaseClient {

    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    /**
     * Добавляет новый предмет для указанного пользователя.
     *
     * @param userId идентификатор пользователя, добавляющего предмет
     * @param dto    DTO с данными о предмете
     * @return ResponseEntity с результатом операции
     */
    public ResponseEntity<Object> addItem(long userId, ItemDto dto) {
        return post("", userId, dto);
    }

    /**
     * Обновляет информацию о существующем предмете.
     *
     * @param userId идентификатор пользователя, обновляющего предмет
     * @param id     идентификатор предмета
     * @param dto    DTO с новыми данными о предмете
     * @return ResponseEntity с результатом операции
     */
    public ResponseEntity<Object> updateItem(long userId, long id, ItemDto dto) {
        return patch("/" + id, userId, dto);
    }

    /**
     * Получает информацию о предмете по его идентификатору.
     *
     * @param userId идентификатор пользователя
     * @param id     идентификатор предмета
     * @return ResponseEntity с результатом операции
     */
    public ResponseEntity<Object> getById(long userId, long id) {
        return get("/" + id, userId);
    }

    /**
     * Получает список предметов, принадлежащих указанному пользователю.
     *
     * @param userId идентификатор владельца предметов
     * @return ResponseEntity с результатом операции
     */
    public ResponseEntity<Object> getItemsOfOwner(long userId) {
        return get("", userId);
    }

    /**
     * Ищет предметы по заданному тексту.
     *
     * @param userId идентификатор пользователя
     * @param text   текст для поиска
     * @return ResponseEntity с результатом операции
     */
    public ResponseEntity<Object> searchItems(long userId, String text) {
        Map<String, Object> params = Map.of(
                "text", text
        );
        return get("/search", userId, params);
    }

    /**
     * Добавляет комментарий к предмету.
     *
     * @param authorId идентификатор автора комментария
     * @param itemId   идентификатор предмета
     * @param dto      DTO с данными комментария
     * @return ResponseEntity с результатом операции
     */
    public ResponseEntity<Object> addComment(long authorId, long itemId, CommentDto dto) {
        return post("/" + itemId + "/comment", authorId, dto);
    }
}
