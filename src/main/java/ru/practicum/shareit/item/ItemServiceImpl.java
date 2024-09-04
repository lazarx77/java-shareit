package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.dto.ItemOwnerDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserValidatorService;

import java.util.Collections;
import java.util.List;

/**
 * Класс ItemServiceImpl реализует интерфейс ItemService и предоставляет
 * методы для работы с предметами.
 */
@Service
@AllArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Item addNewItem(Long userId, ItemDto dto) {
        UserValidatorService.validateId(userId);
        log.info("Проверка на наличие пользователя с id: {} ", userId);
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь c id: " + userId + " не найден"));
        log.info("Сохраняем вещь с id: {} ", userId);
        return itemRepository.addNewItem(userId, dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item updateItem(Long userId, Long itemId, ItemDto dto) {
        UserValidatorService.validateId(userId);
        ItemValidatorService.validateId(itemId);
        log.info("Проверка на наличие пользователя с id: {} ", userId);
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь c id: " + userId + " не найден"));
        log.info("Обновление вещи с id: {} ", itemId);
        return itemRepository.updateItem(userId, itemId, dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemDto getItemDtoById(Long itemId) {
        ItemValidatorService.validateId(itemId);
        return ItemMapper.mapToDto(itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь с id = " + itemId + " не найдена")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ItemOwnerDto> getAllItemsOfOwner(Long userId) {
        UserValidatorService.validateId(userId);
        return itemRepository.findByOwnerId(userId).stream().map(ItemMapper::mapToDtoOwner).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ItemDto> searchItems(String text) {
        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }
        return itemRepository.findAll().stream()
                .filter(item -> item.isAvailable()
                        && ((item.getName().toLowerCase().contains(text.toLowerCase())) ||
                        (item.getDescription().toLowerCase().contains(text.toLowerCase()))))
                .map(ItemMapper::mapToDto).toList();
    }
}
