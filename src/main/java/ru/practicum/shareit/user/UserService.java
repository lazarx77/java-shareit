package ru.practicum.shareit.user;

import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;


/**
 * Интерфейс UserService определяет контракт для работы с пользователями.
 * Он предоставляет методы для выполнения операций CRUD (создание, чтение, обновление, удаление)
 * над объектами типа User и их представлениями типа UserDto.
 */
public interface UserService {

    /**
     * Получает список всех пользователей.
     *
     * @return Список объектов UserDto, представляющих всех пользователей.
     */
    List<UserDto> getAll();

    /**
     * Создает нового пользователя.
     *
     * @param user Объект пользователя, который необходимо создать.
     * @return Созданный объект пользователя.
     * @throws IllegalArgumentException если данные пользователя некорректны.
     */
    User createUser(User user);

    /**
     * Обновляет информацию о существующем пользователе.
     *
     * @param id          Уникальный идентификатор пользователя, которого необходимо обновить.
     * @param updatedUser Объект пользователя с обновленными данными.
     * @return Обновленный объект пользователя.
     * @throws NotFoundException если пользователь с указанным идентификатором не найден.
     */
    User update(Long id, User updatedUser);

    /**
     * Находит пользователя по его уникальному идентификатору.
     *
     * @param id Уникальный идентификатор пользователя.
     * @return Объект UserDto, представляющий найденного пользователя.
     * @throws NotFoundException если пользователь с указанным идентификатором не найден.
     */
    UserDto findById(Long id);

    /**
     * Удаляет пользователя по его уникальному идентификатору.
     *
     * @param id Уникальный идентификатор пользователя, которого необходимо удалить.
     * @throws NotFoundException если пользователь с указанным идентификатором не найден.
     */
    void delete(Long id);
}
