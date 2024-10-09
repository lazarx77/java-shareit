package ru.practicum.shareit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс приложения ShareIt(серверная часть).
 * <p>
 * Этот класс является точкой входа в приложение, использующее Spring Boot.
 * Он запускает приложение и инициализирует контекст Spring.
 */
@SpringBootApplication
public class ShareItApp {

    public static void main(String[] args) {
        SpringApplication.run(ShareItApp.class, args);
    }
}
