package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByBooker_idOrItemOwner_id(Long id, Long userId);

    LinkedList<Booking> findAllBookingsByBooker_idOrderByStartDateDesc(Long bookerId);

    LinkedList<Booking> findAllByBooker_IdAndStartDateBeforeAndEndDateAfterOrderByStartDateDesc(Long bookerId,
                                                                                                LocalDateTime nowStart,
                                                                                                LocalDateTime nowEnd);

    LinkedList<Booking> findAllByBooker_idAndEndDateAfterOrderByStartDateDesc(Long bookerId,
                                                                              LocalDateTime nowEnd);

    LinkedList<Booking> findAllByBooker_idAndStartDateAfterOrderByStartDateDesc(Long bookerId,
                                                                                LocalDateTime nowStart);

    LinkedList<Booking> findAllByBooker_idAndStatusOrderByStartDateDesc(Long bookerId, Status status);

    LinkedList<Booking> findAllByItem_Owner_idOrderByStartDateDesc(Long ownerId);

    LinkedList<Booking> findAllByItem_Owner_idAndStartDateBeforeAndEndDateAfterOrderByStartDateDesc(
            Long ownerId, LocalDateTime nowStart, LocalDateTime nowEnd);

    LinkedList<Booking> findAllByItem_Owner_idAndEndDateAfterOrderByStartDateDesc(Long bookerId,
                                                                                  LocalDateTime nowEnd);

    LinkedList<Booking> findAllByItem_Owner_idAndStartDateAfterOrderByStartDateDesc(Long bookerId,
                                                                                    LocalDateTime nowStart);

    LinkedList<Booking> findAllByItem_Owner_idAndStatusOrderByStartDateDesc(Long bookerId, Status status);

    Optional<Booking> findFirstByItem_Owner_idAndStartDateBeforeOrderByStartDateDesc(Long ownerId,
                                                                                     LocalDateTime nowTime);

    Optional<Booking> findFirstByItem_Owner_idAndStartDateAfterOrderByStartDateAsc(Long ownerId,
                                                                                   LocalDateTime nowTome);
}
