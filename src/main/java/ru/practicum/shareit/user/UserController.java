package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.List;

/**
 * Контроллер UserController обрабатывает HTTP-запросы, связанные с пользователями.
 * Он предоставляет RESTful API для выполнения операций над пользователями,
 * таких как создание, получение, обновление и удаление пользователей.
 */
@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Обрабатывает GET-запрос для получения списка всех пользователей.
     *
     * @return Список объектов UserDto, представляющих всех пользователей.
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers().stream().map(UserMapper::mapToDto).toList();
    }

    /**
     * Обрабатывает POST-запрос для создания нового пользователя.
     *
     * @param user Объект пользователя, который необходимо создать.
     * @return Созданный объект пользователя.
     * @throws IllegalArgumentException если данные пользователя некорректны.
     */
    @PostMapping
    public User saveNewUser(@Validated @RequestBody User user) {
        return userService.addUser(user);
    }

    /**
     * Обрабатывает GET-запрос для получения пользователя по его уникальному идентификатору.
     *
     * @param id Уникальный идентификатор пользователя.
     * @return Объект UserDto, представляющий найденного пользователя.
     * @throws NotFoundException если пользователь с указанным идентификатором не найден.
     */
    @GetMapping("/{id}")
    public UserDto findById(@PathVariable("id") long id) {
        return UserMapper.mapToDto(userService.findUserById(id));
    }

    /**
     * Обрабатывает PATCH-запрос для обновления информации о пользователе.
     *
     * @param id   Уникальный идентификатор пользователя, которого необходимо обновить.
     * @param user Объект пользователя с обновленными данными.
     * @return Обновленный объект пользователя.
     * @throws NotFoundException если пользователь с указанным идентификатором не найден.
     */
    @PatchMapping("/{id}")
    public User update(@PathVariable("id") long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    /**
     * Обрабатывает DELETE-запрос для удаления пользователя по его уникальному идентификатору.
     *
     * @param id Уникальный идентификатор пользователя, которого необходимо удалить.
     * @throws NotFoundException если пользователь с указанным идентификатором не найден.
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        userService.deleteUser(id);
    }
}
