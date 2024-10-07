package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.shareit.exception.EmailDoubleException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
class UserServiceImplIT {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        user = new User(1L, "name", "email@mail.ru");
    }

    @Test
    void addUser_whenUserIsValid_userIsSaved() {
        User savedUser = userService.addUser(user);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("name");
        assertThat(savedUser.getEmail()).isEqualTo("email@mail.ru");

        List<User> users = userService.findAllUsers();
        assertThat(users).hasSize(1);
        assertThat(users.getFirst()).isEqualTo(savedUser);
    }

    @Test
    void findUserById_whenUserExists_userIsReturned() {
        User addedUser = userService.addUser(user);
        User foundUser = userService.findUserById(addedUser.getId());
        assertThat(foundUser).isEqualTo(user);
    }

    @Test
    void findUserById_whenUserNotFound_throwsNotFoundException() {
        assertThrows(NotFoundException.class, () -> userService.findUserById(999L));
    }

    @Test
    void updateUser_whenUserExists_userIsUpdated() {
        userService.addUser(user);
        User updatedUser = new User(1L, "updatedName", "updatedEmail@mail.ru");
        User result = userService.updateUser(user.getId(), updatedUser);

        assertThat(result.getName()).isEqualTo("updatedName");
        assertThat(result.getEmail()).isEqualTo("updatedEmail@mail.ru");
    }

    @Test
    void deleteUser_whenUserExists_userIsDeleted() {
        userService.addUser(user);
        userService.deleteUser(user.getId());

        assertThrows(NotFoundException.class, () -> userService.findUserById(user.getId()));
    }

    @Test
    void addUser_whenEmailIsNotUnique_throwsEmailNotUniqueException() {
        userService.addUser(user);
        User anotherUser = new User(2L, "anotherName", "email@mail.ru");

        assertThrows(EmailDoubleException.class, () -> userService.addUser(anotherUser));
    }
}
