package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностями бронирования.
 * <p>
 * Предоставляет методы для выполнения
 * операций с базой данных, связанных с бронированиями, включая поиск
 * бронирований по различным критериям.
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Находит все бронирования, сделанные пользователем, отсортированные по времени начала в порядке убывания.
     *
     * @param bookerId идентификатор пользователя, чьи бронирования нужно найти
     * @return список бронирований, сделанных пользователем
     */
    LinkedList<Booking> findAllBookingsByBooker_idOrderByStartDesc(Long bookerId);

    /**
     * Находит все бронирования, сделанные пользователем, которые начинаются до текущего времени
     * и заканчиваются после текущего времени, отсортированные по времени начала в порядке убывания.
     *
     * @param bookerId идентификатор пользователя
     * @param nowStart текущее время начала
     * @param nowEnd текущее время окончания
     * @return список бронирований, соответствующих критериям
     */
    LinkedList<Booking> findAllByBooker_idAndStartBeforeAndEndAfterOrderByStartDesc(Long bookerId,
                                                                                    LocalDateTime nowStart,
                                                                                    LocalDateTime nowEnd);

    /**
     * Находит все бронирования, сделанные пользователем, которые заканчиваются после текущего времени,
     * отсортированные по времени начала в порядке убывания.
     *
     * @param bookerId идентификатор пользователя
     * @param nowEnd текущее время окончания
     * @return список бронирований, соответствующих критериям
     */
    LinkedList<Booking> findAllByBooker_idAndEndAfterOrderByStartDesc(Long bookerId,
                                                                      LocalDateTime nowEnd);

    /**
     * Находит все бронирования, сделанные пользователем, которые начинаются после текущего времени,
     * отсортированные по времени начала в порядке убывания.
     *
     * @param bookerId идентификатор пользователя
     * @param nowStart текущее время начала
     * @return список бронирований, соответствующих критериям
     */
    LinkedList<Booking> findAllByBooker_idAndStartAfterOrderByStartDesc(Long bookerId,
                                                                        LocalDateTime nowStart);

    /**
     * Находит все бронирования, сделанные пользователем с определенным статусом,
     * отсортированные по времени начала в порядке убывания.
     *
     * @param bookerId идентификатор пользователя
     * @param status статус бронирования
     * @return список бронирований, соответствующих критериям
     */
    LinkedList<Booking> findAllByBooker_idAndStatusOrderByStartDesc(Long bookerId, Status status);

    /**
     * Находит все бронирования, связанные с предметами, принадлежащими пользователю,
     * отсортированные по времени начала в порядке убывания.
     *
     * @param ownerId идентификатор владельца предметов
     * @return список бронирований, соответствующих критериям
     */
    LinkedList<Booking> findAllByItem_Owner_idOrderByStartDesc(Long ownerId);

    /**
     * Находит все бронирования, связанные с предметами, принадлежащими пользователю,
     * которые начинаются до текущего времени и заканчиваются после текущего времени,
     * отсортированные по времени начала в порядке убывания.
     *
     * @param ownerId идентификатор владельца предметов
     * @param nowStart текущее время начала
     * @param nowEnd текущее время окончания
     * @return список бронирований, соответствующих критериям
     */
    LinkedList<Booking> findAllByItem_Owner_idAndStartBeforeAndEndAfterOrderByStartDesc(
            Long ownerId, LocalDateTime nowStart, LocalDateTime nowEnd);

    /**
     * Находит все бронирования, связанные с предметами, принадлежащими пользователю,
     * которые заканчиваются после текущего времени, отсортированные по времени начала в порядке убывания.
     *
     * @param bookerId идентификатор пользователя
     * @param nowEnd текущее время окончания
     * @return список бронирований, соответствующих критериям
     */
    LinkedList<Booking> findAllByItem_Owner_idAndEndAfterOrderByStartDesc(Long bookerId,
                                                                          LocalDateTime nowEnd);

    /**
     * Находит все бронирования, связанные с предметами, принадлежащими пользователю,
     * которые начинаются после текущего времени, отсортированные по времени начала в порядке убывания.
     *
     * @param bookerId идентификатор пользователя
     * @param nowStart текущее время начала
     * @return список бронирований, соответствующих критериям
     */
    LinkedList<Booking> findAllByItem_Owner_idAndStartAfterOrderByStartDesc(Long bookerId,
                                                                            LocalDateTime nowStart);

    /**
     * Находит все бронирования, связанные с предметами, принадлежащими пользователю,
     * с определенным статусом, отсортированные по времени начала в порядке убывания.
     *
     * @param bookerId идентификатор пользователя
     * @param status статус бронирования
     * @return список бронирований, соответствующих критериям
     */
    LinkedList<Booking> findAllByItem_Owner_idAndStatusOrderByStartDesc(Long bookerId, Status status);

    /**
     * Находит первое бронирование, связанное с предметами, принадлежащими пользователю,
     * которое начинается до текущего времени, отсортированное по времени начала в порядке убывания.
     *
     * @param ownerId идентификатор владельца предметов
     * @param nowTime текущее время
     * @return опциональное бронирование, соответствующее критериям
     */
    Optional<Booking> findFirstByItem_Owner_idAndStartBeforeOrderByStartDesc(Long ownerId,
                                                                             LocalDateTime nowTime);

    /**
     * Находит первое бронирование, связанное с предметами, принадлежащими пользователю,
     * которое начинается после текущего времени, отсортированное по времени начала в порядке возрастания.
     *
     * @param ownerId идентификатор владельца предметов
     * @param nowTome текущее время
     * @return опциональное бронирование, соответствующее критериям
     */
    Optional<Booking> findFirstByItem_Owner_idAndStartAfterOrderByStartAsc(Long ownerId,
                                                                           LocalDateTime nowTome);

    /**
     * Находит бронирование по идентификатору предмета и идентификатору бронирующего,
     * которое заканчивается до текущего времени.
     *
     * @param itemId идентификатор предмета
     * @param bookerId идентификатор бронирующего
     * @param now текущее время
     * @return опциональное бронирование, соответствующее критериям
     */
    Optional<Booking> findByItemIdAndBookerIdAndEndBefore(Long itemId, Long bookerId, LocalDateTime now);
}
