## Описание

Этот проект содержит набор автоматизированных тестов для API `reqres.in`, с использованием библиотеки `RestAssured`, фреймворк `JUnit 5`, и систему отчетов `Allure`. Тесты проверяют ключевые функции API, такие как получение списка пользователей, создание и обновление пользователей, а также обработку неудачных попыток входа в систему.

## Структура проекта

- **models**: Пакет с моделями данных, используемых в запросах и ответах API:
    - `ErrorResponseModel`: Модель для обработки ошибок.
    - `LoginRequestModel`: Модель для отправки данных при логине.
    - `SupportModel`: Модель для информации о поддержке.
    - `UserDataModel`: Модель данных пользователя.
    - `UserListResponseModel`: Модель для ответа на запрос списка пользователей.
    - `UserRequestModel`: Модель для создания и обновления пользователя.
    - `UserResponseModel`: Модель ответа на создание или обновление пользователя.


- **specs**: Пакет для спецификаций ответов API:
    - `ResponseSpecs`: Содержит спецификации успешных и ошибочных ответов API.


- **helpers**: Вспомогательные классы:
    - `CustomApiListener`: Кастомный слушатель для интеграции с Allure, использующий шаблоны для логирования запросов и ответов.


- **tests**: Пакет с тестовыми классами:
    - `ReqresInTests`: Основной класс, содержащий тесты для различных API-запросов.
    - `TestBase`: Базовый класс для настройки тестов, включающий установку базового URL и фильтров логирования.

## Запуск тестов

***Локальный запуск:***
```bash
gradle clean test
```

## Тесты

1. **getUsersListTest**: Проверяет получение списка пользователей на определенной странице.
2. **createUserTest**: Проверяет создание нового пользователя с заданными параметрами.
3. **updateUserTest**: Проверяет обновление данных существующего пользователя.
4. **unsuccessfulLoginTest**: Проверяет обработку неудачного входа в систему при отсутствии пароля.
5. **getUsersListWithDelayTest**: Проверяет получение списка пользователей с искусственной задержкой.
