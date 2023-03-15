## API для АЗС
выполнил Казакпаев Соломон

Данная API запускается на локальном сервере на порте 55567 (`localhost:55567/`). 
Чтобы поменять порт, нужно изменить передаваемый параметр функции `app.start(*your port*)` в классе Main.java

API использует базу данных SQLite, файл которой должен располагаться на вашем компьютере. 
Чтобы поменять путь к базе данных, нужно изменить значение переменной `DB_URL` в классе DatabaseConfiguration.java.
Путь к базе данных должен выглядеть так: `jdbc:sqlite:*your path*`.

Для авторизации на API используется протокол Basic Auth.

API включает в себя 6 сущностей:
1. **user** (в запросе `localhost:55567/user/`)
2. **gas station** (в запросе `localhost:55567/station/`)
3. **column** (в запросе `localhost:55567/column/`)
4. **fuel** (в запросе `localhost:55567/fuel/`)
5. **fuel-column** (в запросе `localhost:55567/regfuel/`)
6. **check** (в запросе `localhost:55567/check/`)


**user**:
- id (int)
- fname (string)
- lname (string)
- phoneNumber (string)
- password (string)
- role (string, *USER* / *OWNER* / *ADMIN*)


**gas station**
- id (int)
- name (string)
- address (string)


**column**
- id (int)
- number (string)
- gasStation (id)


**fuel**
- id (int)
- name (string, *A92* / *A95* / *A98* / *A100* / *DIESEL* / *GAS*)
- price (double)
- gasStation (id)


**fuel-column**
- id (int)
- fuel (id)
- column (id)


**check**
- id (int)
- quantity (double)
- gasStation (id)
- user (id)
- fuel (id)



Каждая сущность имеет CRUD, то есть к каждой из них применимы такие запросы, как **get, post, patch, delete**.

#### GET запросы

вид: `localhost:55567/*entity*/`

можно вытаскивать сущности по id, для этого пропишите параметр запроса id (например, `localhost:55567/user/?id=1`)

выдает неполную информацию о **user** (все, кроме номера телефона и пароля).


#### POST запросы

вид: `localhost:55567/*entity*/`

в тело необходимо передать объект в формате JSON

чтобы создавать объекты (кроме **user**), требуется ввести в Basic Auth данные пользователя с ролью **ADMIN** или **OWNER**.

пользователь с ролью **USER** может быть зарегистрирован без Basic Auth.

Добавлять пользователей с ролью **ADMIN** можно только поменяв роль в файле базы данных.
В дальнейшем пользователь с ролью **ADMIN** имеет полномочия создавать пользователей всех ролей.


#### PATCH запросы

вид: `localhost:55567/*entity*/`

в тело необходимо передать объект в формате JSON

чтобы редактировать объекты (кроме **user**), требуется ввести в Basic Auth данные пользователя с ролью **ADMIN** или **OWNER**.


#### DELETE запрос

вид: `localhost:55567/*entity*/*id*`

удаление происходит по id сущности

полномочия удалять объекты есть только у пользователей с ролью **ADMIN**.