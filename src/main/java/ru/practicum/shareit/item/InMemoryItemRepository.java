//package ru.practicum.shareit.item;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Repository;
//import ru.practicum.shareit.exception.ItemDoNotBelongToUser;
//import ru.practicum.shareit.exception.NotFoundException;
//import ru.practicum.shareit.item.dto.ItemDto;
//import ru.practicum.shareit.item.model.Item;
//import ru.practicum.shareit.user.UserService;
//import ru.practicum.shareit.user.dto.UserMapper;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
///**
// * Класс InMemoryItemRepository предоставляет
// * методы для работы с предметами в памяти.
// */
//@Repository
//@Slf4j
//public class InMemoryItemRepository implements ItemRepository {
//
//    private final Map<Long, Item> items = new HashMap<>();
//    private Long id = 1L;
//    private final UserService userService;
//
//    public InMemoryItemRepository(UserService userService) {
//        this.userService = userService;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Item addNewItem(Long userId, ItemDto dto) {
//        Item item = new Item();
//        item.setId(id);
//        item.setName(dto.getName());
//        item.setDescription(dto.getDescription());
//        if (dto.isAvailable() != null) {
//            item.setAvailable(dto.isAvailable());
//        }
//        item.setOwner(UserMapper.mapToUser(userService.findUserById(userId)));
//        items.put(id, item);
//        id++;
//        return item;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Item updateItem(Long userId, Long itemId, ItemDto dto) {
//        Item item = items.get(itemId);
//        if (item == null) {
//            throw new NotFoundException("Вещь с id = " + itemId + " не найдена");
//        }
//        if (!item.getOwner().getId().equals(userId)) {
//            throw new ItemDoNotBelongToUser("Вещь с id = " + itemId + " не принадлежит пользователю с id = " + userId);
//        }
//        if (dto.getName() != null) {
//            item.setName(dto.getName());
//        }
//        if (dto.getDescription() != null) {
//            item.setDescription(dto.getDescription());
//        }
//        if (dto.isAvailable() != null) {
//            item.setAvailable(dto.isAvailable());
//        }
//        items.put(itemId, item);
//        return item;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Optional<Item> findById(Long itemId) {
//        return Optional.ofNullable(items.get(itemId));
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public List<Item> findByOwnerId(Long ownerId) {
//        return items.values().stream().filter(owner -> owner.getOwner().getId().equals(ownerId)).toList();
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public List<Item> findAll() {
//        return items.values().stream().toList();
//    }
//}
