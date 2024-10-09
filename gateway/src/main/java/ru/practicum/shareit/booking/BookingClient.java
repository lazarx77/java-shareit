package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.client.BaseClient;

import java.util.Map;

/**
 * Клиент для взаимодействия с API бронирования.
 * <p>
 * Этот сервис предоставляет методы для выполнения операций
 * с бронированиями, таких как создание бронирования, изменение
 * статуса бронирования и получение информации о бронированиях.
 * </p>
 */
@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    /**
     * Создает новое бронирование для указанного пользователя.
     *
     * @param userId     идентификатор пользователя, создающего бронирование
     * @param requestDto DTO с данными о бронировании
     * @return ResponseEntity с результатом операции
     */
    public ResponseEntity<Object> bookItem(long userId, BookItemRequestDto requestDto) {
        return post("", userId, requestDto);
    }

    /**
     * Изменяет статус существующего бронирования.
     *
     * @param id       идентификатор бронирования
     * @param approved новый статус одобрения
     * @param userId   идентификатор пользователя, изменяющего статус
     * @return ResponseEntity с результатом операции
     */
    public ResponseEntity<Object> changeStatus(long id, boolean approved, long userId) {
        return patch("/" + id + "?approved=" + approved, userId);
    }

    /**
     * Получает список бронирований для указанного пользователя с заданным состоянием.
     *
     * @param userId идентификатор пользователя
     * @param state  состояние бронирования
     * @param from   индекс первого элемента для пагинации
     * @param size   количество элементов на странице
     * @return ResponseEntity с результатом операции
     */
    public ResponseEntity<Object> getBookings(long userId, BookingState state, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "state", state.name(),
                "from", from,
                "size", size
        );
        return get("?state={state}&from={from}&size={size}", userId, parameters);
    }

    /**
     * Получает информацию о конкретном бронировании по его идентификатору.
     *
     * @param userId    идентификатор пользователя
     * @param bookingId идентификатор бронирования
     * @return ResponseEntity с результатом операции
     */
    public ResponseEntity<Object> getBooking(long userId, Long bookingId) {
        return get("/" + bookingId, userId);
    }

    /**
     * Получает список бронирований, принадлежащих владельцу, с заданным состоянием.
     *
     * @param userId идентификатор владельца
     * @param state  состояние бронирования
     * @return ResponseEntity с результатом операции
     */
    public ResponseEntity<Object> getOwnerBookings(long userId, BookingState state) {
        Map<String, Object> params = Map.of(
                "state", state.name()
        );
        return get("/owner", userId, params);
    }
}
