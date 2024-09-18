//package ru.practicum.shareit.user;
//
//import org.springframework.stereotype.Repository;
//import ru.practicum.shareit.exception.NotFoundException;
//import ru.practicum.shareit.user.model.User;
//
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
///**
// * Класс InMemoryUserRepository
// * предоставляет функциональность для хранения пользователей в памяти
// * с использованием HashMap.
// *
// * <p>Этот класс предназначен для использования в качестве временного хранилища
// * пользователей, что позволяет быстро выполнять операции CRUD без обращения к базе данных.</p>
// */
//@Repository
//public class InMemoryUserRepository implements UserRepository {
//
//    private final Map<Long, User> users = new HashMap<>();
//    private Long id = 1L;
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Collection<User> getAll() {
//        return users.values().stream().toList();
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public User createUser(User user) {
//        user.setId(id);
//        users.put(id, user);
//        id++;
//        return user;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public User update(Long id, User updatedUser) {
//        if (!users.containsKey(id)) {
//            throw new NotFoundException("Пользователь с таким id = " + updatedUser.getId() + " не существует");
//        }
//        updatedUser.setId(id);
//        if (updatedUser.getEmail() != null) {
//            UserValidatorService.validateEmailDouble(updatedUser, users.values().stream().toList());
//        } else {
//            updatedUser.setEmail(users.get(id).getEmail());
//        }
//        if (updatedUser.getName() == null) {
//            updatedUser.setName(users.get(id).getName());
//        }
//        users.put(id, updatedUser);
//        return updatedUser;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Optional<User> findById(Long id) {
//        return Optional.ofNullable(users.get(id));
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public void delete(Long id) {
//        users.remove(id);
//    }
//}
