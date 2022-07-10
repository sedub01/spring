# <p align = "center"> Тестовое задание для ООО Арт План Софтвер </p>

<p align = "left"> Первое разминочное задание заключается в инверсии строки и замера времени работы функции</p>

<p align = "left"> Само задание заключается в проектировании приложения, которое обладает следующим функционалом: </p>

- создание пользователя (регистрация)

- проверка доступности логина (для незарегистрированных)

- количество неудачных попыток авторизации - не должно превышать 10 за 1 час и сбрасываться при успешной авторизации

- созданный пользователь может создавать/редактировать/удалять животных, получить их список и получить детали животного по ID

<p align = "left">Все взаимодействие должно происходить с использованием JSON форамата данных: все ошибки так же передаются в виде Json</p>

<p align = "left">Для создания БД использовалась MySQL, вот код для ее создания: </p>

```SQL
CREATE SCHEMA animals;

CREATE TABLE animals.users (
    login varchar(100) primary key,
    user JSON,
    lastTimeAttempt datetime
);

insert into animals.users values ('sedub01', '
{
  "login": "sedub01",
  "password": "123456",
  "animals": [
    {
      "id": 1,
      "name": "pushok",
      "kind": "cat",
      "birth_date": "2012-03-04",
      "sex": "f"
    },{
    "id": 2,
    "name": "shilen",
    "kind": "dog",
    "birth_date": "2016-09-23",
    "sex": "m"
    }
  ]
}
', null)

insert into animals.users values ('koshmarikot', '
{
  "login": "koshmarikot",
  "password": "expert",
  "animals": [
    {
      "id": 1,
      "name": "dushnila",
      "kind": "snake",
      "birth_date": "2009-08-01",
      "sex": "m"
    },{
    "id": 2,
    "name": "psina",
    "kind": "wolf",
    "birth_date": "2002-04-13",
    "sex": "f"
    }
  ]
}
', null)
```