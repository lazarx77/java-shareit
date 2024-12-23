# java-shareit
Приложение для обмена предметами.

## Основные функции

* Регистрация и авторизация пользователей
* Управление профилем пользователя
* Добавление, удаление и обновление предметов
* Добавление, удаление и обновление запросов на предметы
* Бронирование и сдача предметов в аренду
* Получение списка предметов, доступных для аренды
* Получение списка бронирований и запросов на предметы

## Технологии и инструменты

* Язык программирования: Java Amazon Corretto 21
* Фреймворк: Spring Boot
* База данных: PostgresSQL
* Docker
* Docker-compose

## Автор
Олег Лазаренко

## Схема базы данных

![shareit - public.png](shareit%20-%20public.png)

## Доступ к функциям приложения осуществляется через REST API

# BookingController

Контроллер для управления бронированиями.

Этот контроллер обрабатывает HTTP-запросы, связанные с бронированием предметов, включая создание бронирования, изменение статуса, получение информации о бронированиях и получение бронирований владельца.

## Эндпоинты

### Создание нового бронирования

- **URL**: `/bookings`
- **Метод**: `POST`
- **Заголовки**:
    - `X-Sharer-User-Id` (long) - идентификатор пользователя, создающего бронирование
- **Тело запроса**: `BookItemRequestDto` - DTO с данными о бронировании
- **Ответ**: `ResponseEntity<Object>` - результат операции
- **Ошибки**:
    - `ValidationException` - если время начала или окончания бронирования некорректно

### Изменение статуса бронирования

- **URL**: `/bookings/{bookingId}`
- **Метод**: `PATCH`
- **Параметры**:
    - `bookingId` (long) - идентификатор бронирования
- **Запрос**:
    - `approved` (boolean) - новый статус одобрения
    - `X-Sharer-User-Id` (Long) - идентификатор пользователя, изменяющего статус
- **Ответ**: `ResponseEntity<Object>` - результат операции

### Получение информации о бронировании

- **URL**: `/bookings/{bookingId}`
- **Метод**: `GET`
- **Параметры**:
    - `bookingId` (Long) - идентификатор бронирования
- **Заголовки**:
    - `X-Sharer-User-Id` (long) - идентификатор пользователя
- **Ответ**: `ResponseEntity<Object>` - результат операции

### Получение списка бронирований пользователя

- **URL**: `/bookings`
- **Метод**: `GET`
- **Заголовки**:
    - `X-Sharer-User-Id` (long) - идентификатор пользователя
- **Параметры**:
    - `state` (String, по умолчанию: "all") - состояние бронирования
    - `from` (Integer, по умолчанию: 0) - индекс первого элемента для пагинации
    - `size` (Integer, по умолчанию: 10) - количество элементов на странице
- **Ответ**: `ResponseEntity<Object>` - результат операции

### Получение списка бронирований владельца

- **URL**: `/bookings/owner`
- **Метод**: `GET`
- **Заголовки**:
    - `X-Sharer-User-Id` (Long) - идентификатор владельца
- **Параметры**:
    - `state` (String, по умолчанию: "all") - состояние бронирования
- **Ответ**: `ResponseEntity<Object>` - результат операции

## Примечания

- Контроллер использует аннотации валидации для проверки входных параметров.
- Логирование используется для отслеживания операций и состояния.
- В случае некорректных параметров выбрасываются исключения, которые обрабатываются на уровне сервиса.

`BookingController` предоставляет API для управления бронированиями, позволяя пользователям создавать, изменять и 
получать информацию о своих бронированиях, а также получать информацию о бронированиях, принадлежащих владельцам. 
Это делает его важной частью системы управления бронированиями.

# ItemController

Контроллер для управления предметами.

Этот контроллер обрабатывает HTTP-запросы, связанные с предметами, включая добавление, обновление, получение информации о предмете, получение предметов владельца, поиск предметов и добавление комментариев.

## Эндпоинты

### Добавление нового предмета

- **URL**: `/items`
- **Метод**: `POST`
- **Заголовки**:
    - `X-Sharer-User-Id` (Long) - идентификатор пользователя, добавляющего предмет
- **Тело запроса**: `ItemDto` - DTO с данными о предмете
- **Ответ**: `ResponseEntity<Object>` - результат операции

### Обновление существующего предмета

- **URL**: `/items/{itemId}`
- **Метод**: `PATCH`
- **Параметры**:
    - `itemId` (Long) - идентификатор предмета
- **Заголовки**:
    - `X-Sharer-User-Id` (Long) - идентификатор пользователя, обновляющего предмет
- **Тело запроса**: `ItemDto` - DTO с новыми данными о предмете
- **Ответ**: `ResponseEntity<Object>` - результат операции

### Получение информации о предмете

- **URL**: `/items/{itemId}`
- **Метод**: `GET`
- **Параметры**:
    - `itemId` (Long) - идентификатор предмета
- **Заголовки**:
    - `X-Sharer-User-Id` (Long) - идентификатор пользователя
- **Ответ**: `ResponseEntity<Object>` - результат операции

### Получение списка предметов владельца

- **URL**: `/items`
- **Метод**: `GET`
- **Заголовки**:
    - `X-Sharer-User-Id` (Long) - идентификатор владельца предметов
- **Ответ**: `ResponseEntity<Object>` - результат операции

### Поиск предметов

- **URL**: `/items/search`
- **Метод**: `GET`
- **Заголовки**:
    - `X-Sharer-User-Id` (Long) - идентификатор пользователя
- **Параметры**:
    - `text` (String) - текст для поиска
- **Ответ**: `ResponseEntity<Object>` - результат операции

### Добавление комментария к предмету

- **URL**: `/items/{itemId}/comment`
- **Метод**: `POST`
- **Параметры**:
    - `itemId` (Long) - идентификатор предмета
- **Заголовки**:
    - `X-Sharer-User-Id` (Long) - идентификатор автора комментария
- **Тело запроса**: `CommentDto` - DTO с данными комментария
- **Ответ**: `ResponseEntity<Object>` - результат операции

## Примечания

- Контроллер использует аннотации валидации для проверки входных параметров.
- Логирование используется для отслеживания операций и состояния.
- Все операции возвращают `ResponseEntity`, что позволяет гибко управлять ответами и статусами.

`ItemController` предоставляет API для управления предметами, позволяя пользователям добавлять, обновлять, получать
информацию о своих предметах, а также искать предметы и оставлять комментарии. Это делает его важной частью системы 
управления предметами.

# RequestController

Контроллер для обработки запросов, связанных с предметами. Обеспечивает функциональность для добавления и получения запросов на предметы.

## Эндпоинты

### Добавление нового запроса на предмет

- **URL**: `/requests`
- **Метод**: `POST`
- **Заголовки**:
    - `X-Sharer-User-Id` (long) - идентификатор пользователя, создающего запрос
- **Тело запроса**: `NewItemRequestDto` - объект, содержащий данные нового запроса на предмет
- **Ответ**: `ResponseEntity<Object>` - результат операции

### Получение запросов пользователя

- **URL**: `/requests`
- **Метод**: `GET`
- **Заголовки**:
    - `X-Sharer-User-Id` (long) - идентификатор пользователя, чьи запросы нужно получить
- **Ответ**: `ResponseEntity<Object>` - список запросов пользователя

### Получение всех запросов от других пользователей

- **URL**: `/requests/all`
- **Метод**: `GET`
- **Заголовки**:
    - `X-Sharer-User-Id` (long) - идентификатор пользователя, запрашивающего данные
- **Ответ**: `ResponseEntity<Object>` - список запросов от других пользователей

### Получение запроса по идентификатору

- **URL**: `/requests/{requestId}`
- **Метод**: `GET`
- **Параметры**:
    - `requestId` (long) - идентификатор запроса, который нужно получить
- **Заголовки**:
    - `X-Sharer-User-Id` (long) - идентификатор пользователя, запрашивающего данные
- **Ответ**: `ResponseEntity<Object>` - данные запрашиваемого запроса

## Примечания

- Контроллер использует аннотации валидации для проверки входных параметров.
- Логирование используется для отслеживания операций и состояния.
- Все операции возвращают `ResponseEntity`, что позволяет гибко управлять ответами и статусами.

## Примеры запросов

### Добавление нового запроса на предмет

```http
POST /requests HTTP/1.1
Host: your-api-domain.com
X-Sharer-User-Id: 1
Content-Type: application/json

{
    "description": "Нужен инструмент для ремонта"
}
```
`RequestController` предоставляет API для управления запросами на предметы, позволяя пользователям добавлять свои 
запросы, получать информацию о своих запросах, а также просматривать запросы от других пользователей. Это делает 
его важной частью системы управления запросами на предметы.

# UserController

Контроллер для обработки запросов, связанных с пользователями. Обеспечивает функциональность для получения, создания, обновления и удаления пользователей.

## Эндпоинты

### Получение всех пользователей

- **URL**: `/users`
- **Метод**: `GET`
- **Ответ**: `ResponseEntity<Object>` - список всех пользователей

### Сохранение нового пользователя

- **URL**: `/users`
- **Метод**: `POST`
- **Тело запроса**: `UserDto` - объект, содержащий данные нового пользователя
- **Ответ**: `ResponseEntity<Object>` - результат операции

### Поиск пользователя по идентификатору

- **URL**: `/users/{id}`
- **Метод**: `GET`
- **Параметры**:
    - `id` (long) - идентификатор пользователя
- **Ответ**: `ResponseEntity<Object>` - данные найденного пользователя

### Обновление данных пользователя

- **URL**: `/users/{id}`
- **Метод**: `PATCH`
- **Параметры**:
    - `id` (long) - идентификатор пользователя, данные которого нужно обновить
- **Тело запроса**: `UserDto` - объект, содержащий обновленные данные пользователя
- **Ответ**: `ResponseEntity<Object>` - результат операции

### Удаление пользователя

- **URL**: `/users/{id}`
- **Метод**: `DELETE`
- **Параметры**:
    - `id` (long) - идентификатор пользователя, которого нужно удалить
- **Ответ**: `ResponseEntity<Object>` - результат операции

## Примечания

- Контроллер использует аннотации валидации для проверки входных параметров.
- Логирование используется для отслеживания операций и состояния.
- Все операции возвращают `ResponseEntity`, что позволяет гибко управлять ответами и статусами.

`UserController` предоставляет API для управления пользователями, позволяя создавать, обновлять, получать и удалять 
пользователей. Это делает его важной частью системы управления пользователями.

### Подробная документация проекта находится в пакете `javadoc`
