package ru.practicum.shareit.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Класс ErrorResponse представляет собой структуру для хранения информации об ошибках,
 * возникающих в приложении. Он содержит два поля:
 * <ul>
 *     <li><b>error</b> - строка, описывающая тип ошибки;</li>
 *     <li><b>description</b> - строка, предоставляющая дополнительную информацию о причине ошибки.</li>
 * </ul>
 * <p>
 * Данный класс используется для формирования ответов об ошибках, которые могут быть возвращены
 * клиенту в случае возникновения исключительных ситуаций в процессе работы приложения.
 */
@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final String error;
    private final String description;
}
