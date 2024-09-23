package ru.practicum.shareit.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью "Пользователь".
 * Предоставляет методы для выполнения операций с пользователями в базе данных.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Находит пользователя по его адресу электронной почты.
     * Поиск осуществляется с игнорированием регистра.
     *
     * @param email адрес электронной почты пользователя.
     * @return объект Optional, содержащий найденного пользователя, если он существует; иначе - пустой объект.
     */
    @Query("select u from User u " +
            "where upper(u.email) like upper(concat(?1, '%'))")
    Optional<User> findUserByEmail(String email);
}
