# Крестики-нолики

## Требования

- Создать веб приложение (бэкенд API), умеющее играть в крестики нолики
- Приложение должно быть на версиях Java 6-15 с использованием SpringBoot
- Приложение должно уметь вести несколько партий одновременно, партия пользователя идентифицируется каким либо параметром сессии
- Приложение использует JPA Hibernate, каждый ход приложения сохраняется в БД.
- Приложение умеет сделать отмену хода.
- В информации о доске, возвращаемой в каждой команде API кроме состояния доски, передавать также состояние партии (завершена, победитель)
- Все параметры приложения вынести в env переменные
- Сборщик приложения gradle или maven


## Команды Api:
- Начать новую партию, в качестве параметра в метод передается, кто делает в новой партии первый ход: пользователь или машина, в ответе
  возвращается пустая доска (если ход пользователя), иначе доска с ходом машины
- Сделать ход, в качестве параметра передается ход пользователя, в ответе возвращается доска где сделан ответный ход машины,
  либо исключение 403 если такой ход невозможен (партия завершена, или ход запрещен правилами)
- Отменить ход, состояние партии откатывается, в ответе возвращается доска где отменен последний ход машины и ход пользователя,
  если дошли до самого начала партии и пытаются отменить ход, то бросать исключение 404, при этом состояние партии не меняется 
- Получить состояние партии, в ответе возвращается доска с текущим состоянием партии

## База данных

Используется H2, при поднятии приложения создается база данных в памяти, при остановке приложения база данных удаляется.
После поднятия приложения можно подключиться к базе данных по адресу http://localhost:8080/h2-console


Интерактивная доккументация по API доступна по адресу http://localhost:8080/swagger-ui/index.html

