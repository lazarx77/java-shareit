package ru.practicum.shareit.user;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {


    Collection<User> getAll();

    User createUser(User user);

    User update(User updatedUser);

    Optional<User> findById(Long id);
}
