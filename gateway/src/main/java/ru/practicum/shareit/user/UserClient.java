package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.user.dto.UserDto;

/**
 * Клиент для взаимодействия с API по обработке пользователей.
 * Обеспечивает методы для выполнения операций с пользователями, таких как создание, получение, обновление и удаление.
 */
@Service
public class UserClient extends BaseClient {

    private static final String API_PREFIX = "/users";

    @Autowired
    public UserClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    /**
     * Получает всех пользователей.
     *
     * @return ResponseEntity с списком всех пользователей
     */
    public ResponseEntity<Object> getAllUsers() {
        return get("");
    }

    /**
     * Сохраняет нового пользователя.
     *
     * @param dto объект, содержащий данные нового пользователя
     * @return ResponseEntity с результатом операции
     */
    public ResponseEntity<Object> saveNewUser(UserDto dto) {
        return post("", dto);
    }

    /**
     * Находит пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return ResponseEntity с данными найденного пользователя
     */
    public ResponseEntity<Object> findUserById(long id) {
        return get("/" + id);
    }

    /**
     * Обновляет данные пользователя.
     *
     * @param id  идентификатор пользователя, данные которого нужно обновить
     * @param dto объект, содержащий обновленные данные пользователя
     * @return ResponseEntity с результатом операции
     */
    public ResponseEntity<Object> updateUser(long id, UserDto dto) {
        return patch("/" + id, dto);
    }

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя, которого нужно удалить
     * @return ResponseEntity с результатом операции
     */
    public ResponseEntity<Object> deleteUser(long id) {
        return delete("/" + id);
    }
}
