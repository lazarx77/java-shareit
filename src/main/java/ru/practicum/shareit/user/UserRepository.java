package ru.practicum.shareit.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

/**
 * Интерфейс UserRepository определяет контракт для работы с данными пользователей.
 * Он предоставляет методы для выполнения операций CRUD (создание, чтение, обновление, удаление)
 * над объектами типа User.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u " +
            "where upper(u.email) like upper(concat(?1, '%'))")
    Optional<User> findUserByEmail(String email);
}
