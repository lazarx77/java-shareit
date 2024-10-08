package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

/**
 * Утилитный класс для преобразования объектов предметов между различными представлениями (DTO).
 * <p>
 * Этот класс содержит статические методы для преобразования сущностей предметов в DTO
 * и наоборот, а также для добавления информации о комментариях и бронированиях.
 */
public class ItemMapper {

    /**
     * Преобразует объект Item в ItemDto с комментариями.
     *
     * @param item        объект предмета, который нужно преобразовать
     * @param itemService сервис для получения комментариев к предмету
     * @return объект ItemDto, содержащий данные из объекта Item и список комментариев
     */
    public static ItemDto mapToDtoWithComments(Item item, ItemService itemService) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        if (item.getAvailable() != null) {
            dto.setAvailable(item.getAvailable());
        }
        List<Comment> comments = itemService.getComments(item.getId());
        dto.setComments(comments
                .stream()
                .map(CommentMapper::mapToCommentDto)
                .toList());
        return dto;
    }

    /**
     * Преобразует объект Item в ItemDto без комментариев.
     *
     * @param item объект предмета, который нужно преобразовать
     * @return объект ItemDto, содержащий данные из объекта Item
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
     * Преобразует объект ItemDto в объект Item.
     *
     * @param dto объект ItemDto, содержащий данные для создания нового предмета
     * @return объект Item, созданный на основе данных из ItemDto
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
     * Преобразует объект Item в ItemOwnerDto с информацией о бронированиях и комментариях.
     *
     * @param item           объект предмета, который нужно преобразовать
     * @param bookingService сервис для получения информации о бронированиях
     * @param itemService    сервис для получения комментариев к предмету
     * @return объект ItemOwnerDto, содержащий данные о предмете, его бронированиях и комментариях
     */
    public static ItemOwnerDto mapToDtoOwner(Item item, BookingService bookingService, ItemService itemService) {
        ItemOwnerDto dto = new ItemOwnerDto();
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        Booking lastBooking = bookingService.findLastBooking(item);
        Booking futureBooking = bookingService.findFutureBooking(item);
        if (lastBooking != null) {
            dto.setLastBooking(BookingMapper.mapToItemBookingDto(lastBooking));
        }
        if (futureBooking != null) {
            dto.setNextBooking(BookingMapper.mapToItemBookingDto(futureBooking));
        }
        List<Comment> comments = itemService.getComments(item.getId());
        dto.setComments(comments
                .stream()
                .map(CommentMapper::mapToCommentDto)
                .toList());
        return dto;
    }


}
