package ru.example;

public class Animal{
    public int id = 1;
    public String name;
    public String kind;
    public String birth_date;
    public String sex;

    Animal(){} //для взаимодействия с БД пустой конструктор просто необходим (из-за сериализации)
    public Animal(int id, String name, String kind, String birth_date, String sex) {
        this.id = id;
        this.name = name;
        this.kind = kind;
        this.birth_date = birth_date;
        this.sex = sex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}