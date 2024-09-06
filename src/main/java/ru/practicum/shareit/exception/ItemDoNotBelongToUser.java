package ru.practicum.shareit.exception;

/**
 * Исключение ItemDoNotBelongToUser представляет собой пользовательское исключение,
 * которое выбрасывается в случае, если операция с предметом (например, его редактирование
 * или удаление) пытается быть выполненной пользователем, которому этот предмет не принадлежит.
 */
public class ItemDoNotBelongToUser extends RuntimeException {
    public ItemDoNotBelongToUser(String message) {
        super(message);
    }
}
