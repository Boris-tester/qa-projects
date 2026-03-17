# UI Tests - Stellar Burgers

Проект содержит UI автотесты для веб-приложения Stellar Burgers.

Технологии:
- Java 11
- JUnit 4
- Selenium WebDriver
- Maven
- Allure
---
## 📦 Требования

Перед запуском должны быть установлены:

- Java 11
- Maven
- Google Chrome
- (опционально) Yandex Browser
---
# 🚀 Запуск тестов

## ▶ Запуск в Google Chrome

```bash
mvn clean test -Dbrowser=chrome
```
▶ Запуск в Yandex Browser (Windows / PowerShell)

Необходимо указать путь к браузеру и драйверу.

Пример:
```powershell
mvn --% clean test -Dbrowser=yandex -Dyandex.binary=C:\Users\User\AppData\Local\Yandex\YandexBrowser\Application\browser.exe -Dyandex.driver=C:\webdrivers\yandexdriver.exe
```
Где:
yandex.binary — путь к browser.exe
yandex.driver — путь к yandexdriver.exe
---
📊 Генерация Allure отчёта

После выполнения тестов:
```bash
mvn allure:serve
```
Откроется отчёт в браузере.
---
✔ Что покрывают тесты
 Регистрация

- успешная регистрация

- ошибка при коротком пароле (менее 6 символов)

 Вход

- вход по кнопке «Войти в аккаунт»

- вход через «Личный кабинет»

- вход из формы регистрации

- вход из формы восстановления пароля

 Конструктор

- переход к разделу «Булки»

- переход к разделу «Соусы»

- переход к разделу «Начинки»
