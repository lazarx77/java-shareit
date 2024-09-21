package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByBooker_idOrItemOwner_id(Long id, Long userId);

    LinkedList<Booking> findAllBookingsByBooker_idOrderByStartDesc(Long bookerId);

    LinkedList<Booking> findAllByBooker_idAndStartBeforeAndEndAfterOrderByStartDesc(Long bookerId,
                                                                                    LocalDateTime nowStart,
                                                                                    LocalDateTime nowEnd);

    LinkedList<Booking> findAllByBooker_idAndEndAfterOrderByStartDesc(Long bookerId,
                                                                      LocalDateTime nowEnd);

    LinkedList<Booking> findAllByBooker_idAndStartAfterOrderByStartDesc(Long bookerId,
                                                                        LocalDateTime nowStart);

    LinkedList<Booking> findAllByBooker_idAndStatusOrderByStartDesc(Long bookerId, Status status);

    LinkedList<Booking> findAllByItem_Owner_idOrderByStartDesc(Long ownerId);

    LinkedList<Booking> findAllByItem_Owner_idAndStartBeforeAndEndAfterOrderByStartDesc(
            Long ownerId, LocalDateTime nowStart, LocalDateTime nowEnd);

    LinkedList<Booking> findAllByItem_Owner_idAndEndAfterOrderByStartDesc(Long bookerId,
                                                                          LocalDateTime nowEnd);

    LinkedList<Booking> findAllByItem_Owner_idAndStartAfterOrderByStartDesc(Long bookerId,
                                                                            LocalDateTime nowStart);

    LinkedList<Booking> findAllByItem_Owner_idAndStatusOrderByStartDesc(Long bookerId, Status status);

    Optional<Booking> findFirstByItem_Owner_idAndStartBeforeOrderByStartDesc(Long ownerId,
                                                                             LocalDateTime nowTime);

    Optional<Booking> findFirstByItem_Owner_idAndStartAfterOrderByStartAsc(Long ownerId,
                                                                           LocalDateTime nowTome);

    Optional<Booking> findByItemId(Long itemId);

    Optional<Booking> findByItemIdAndBookerIdAndEndBefore(Long itemId, Long bookerId, LocalDateTime now);

}
