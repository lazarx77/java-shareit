package ru.practicum.shareit.user;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

@Controller
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserClient userClient;

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        log.info("Получение всех пользователей");
        return userClient.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<Object> saveNewUser(@Validated @RequestBody UserDto dto) {
        log.info("Сохранение нового пользователя в БД с email {}", dto.getEmail());
        return userClient.saveNewUser(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@Positive @PathVariable("id") long id) {
        log.info("Поиск пользователя по id {}", id);
        return userClient.findUserById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUser(@Positive @PathVariable("id") long id, @RequestBody UserDto dto) {
        log.info("Обновление польззователя id {}", id);
        return userClient.updateUser(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@Positive @PathVariable("id") long id) {
        log.info("Удаление пользователя id {}", id);
        return userClient.deleteUser(id);
    }
}
