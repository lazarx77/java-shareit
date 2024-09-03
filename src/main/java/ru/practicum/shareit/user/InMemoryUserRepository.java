package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Component
public class InMemoryUserRepository implements UserRepository {

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public Collection<User> getAll() {
        return users.values().stream().toList();
    }

    @Override
    public User createUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User updatedUser){
        if (!users.containsKey(updatedUser.getId())) {
            throw new NotFoundException("Пользователь с таким id = " + updatedUser.getId() + " не существует");
        }
        users.put(updatedUser.getId(), updatedUser);
        return updatedUser;
    }

    @Override
    public Optional<User> findById(Long id){
        return Optional.ofNullable(users.get(id));
    }
}
