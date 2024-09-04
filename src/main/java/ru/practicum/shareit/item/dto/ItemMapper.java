package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;

/**
 * Класс ItemMapper предоставляет статические методы для преобразования объектов
 * между различными представлениями данных, такими как {@link Item} и {@link ItemDto}.
 * Этот класс служит для упрощения процесса маппинга данных, позволяя избежать дублирования
 * кода и обеспечивая чистоту архитектуры приложения.
 *
 * <p>Класс содержит следующие методы:</p>
 *
 * <ul>
 *     <li>
 *         {@link #mapToDto(Item)} - преобразует объект типа {@link Item} в объект типа {@link ItemDto}.
 *     </li>
 *     <li>
 *         {@link #mapToItem(ItemDto)} - преобразует объект типа {@link ItemDto} в объект типа {@link Item}.
 *     </li>
 *     <li>
 *         {@link #mapToDtoOwner(Item)} - преобразует объект типа {@link Item} в объект типа {@link ItemOwnerDto},
 *         который содержит информацию о предмете для владельца.
 *     </li>
 * </ul>
 */
public class ItemMapper {

    /**
     * Преобразует объект типа {@link Item} в объект типа {@link ItemDto}.
     *
     * @param item объект типа {@link Item}, который необходимо преобразовать.
     * @return объект типа {@link ItemDto}, содержащий данные из переданного объекта {@link Item}.
     */
    public static ItemDto mapToDto(Item item) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        if (item.getAvailable() != null) {
            dto.setAvailable(item.getAvailable());
        }
        return dto;
    }

    /**
     * Преобразует объект типа {@link ItemDto} в объект типа {@link Item}.
     *
     * @param dto объект типа {@link ItemDto}, который необходимо преобразовать.
     * @return объект типа {@link Item}, содержащий данные из переданного объекта {@link ItemDto}.
     */
    public static Item mapToItem(ItemDto dto) {
        Item item = new Item();
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        if (dto.isAvailable() != null) {
            item.setAvailable(dto.isAvailable());
        }
        item.setAvailable(dto.isAvailable());
        item.setId(dto.getId());
        return item;
    }

    /**
     * Преобразует объект типа {@link Item} в объект типа {@link ItemOwnerDto},
     * который содержит информацию о предмете для владельца.
     *
     * @param item объект типа {@link Item}, который необходимо преобразовать.
     * @return объект типа {@link ItemOwnerDto}, содержащий название и описание предмета.
     */
    public static ItemOwnerDto mapToDtoOwner(Item item) {
        return new ItemOwnerDto(
                item.getName(),
                item.getDescription()
        );
    }
}
