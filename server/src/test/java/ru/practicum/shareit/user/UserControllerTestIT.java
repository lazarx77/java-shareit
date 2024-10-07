package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.StatusAggregator;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTestIT {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    BookingService bookingService;

    @Test
    void getAllUsers() {
    }

    @Test
    void saveNewUser() {
    }

    @SneakyThrows
    @Test
    void findById_whenUserFound_thenReturnStatusIsOkAndUserDto() {
        long userId = 1L;
        UserDto userDto = new UserDto(1L, "name", "email@email.ru");
        User user = new User(1L, "name", "email@email.ru");
        when(userService.findUserById(1L)).thenReturn(user);

        String result = mockMvc.perform(get("/users/{id}", userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(userDto), result);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}