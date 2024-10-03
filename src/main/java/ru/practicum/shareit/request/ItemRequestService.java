package ru.practicum.shareit.request;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

@Transactional(readOnly = true)
public interface ItemRequestService {

    @Transactional
    ItemRequest addNewRequest(Long requestorId, ItemRequest itemRequest);

    List<ItemRequest> getRequestsOfRequestor(Long requestorId);

    List<ItemRequest> getAllRequestsOfOtherUsers(Long userId);

    ItemRequest getRequestById(Long userId, Long requestId);
}
