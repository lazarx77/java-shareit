package ru.practicum.shareit.user;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

/**
 * Контроллер для обработки запросов, связанных с пользователями.
 * Обеспечивает функциональность для получения, создания, обновления и удаления пользователей.
 */
@Controller
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserClient userClient;

    /**
     * Получает всех пользователей.
     *
     * @return ResponseEntity с списком всех пользователей
     */
    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        log.info("Получение всех пользователей");
        return userClient.getAllUsers();
    }

    /**
     * Сохраняет нового пользователя.
     *
     * @param dto объект, содержащий данные нового пользователя
     * @return ResponseEntity с результатом операции
     */
    @PostMapping
    public ResponseEntity<Object> saveNewUser(@Validated @RequestBody UserDto dto) {
        log.info("Сохранение нового пользователя в БД с email {}", dto.getEmail());
        return userClient.saveNewUser(dto);
    }

    /**
     * Находит пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return ResponseEntity с данными найденного пользователя
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@Positive @PathVariable("id") long id) {
        log.info("Поиск пользователя по id {}", id);
        return userClient.findUserById(id);
    }

    /**
     * Обновляет данные пользователя.
     *
     * @param id  идентификатор пользователя, данные которого нужно обновить
     * @param dto объект, содержащий обновленные данные пользователя
     * @return ResponseEntity с результатом операции
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUser(@Positive @PathVariable("id") long id, @RequestBody UserDto dto) {
        log.info("Обновление польззователя id {}", id);
        return userClient.updateUser(id, dto);
    }

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя, которого нужно удалить
     * @return ResponseEntity с результатом операции
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@Positive @PathVariable("id") long id) {
        log.info("Удаление пользователя id {}", id);
        return userClient.deleteUser(id);
    }
}
