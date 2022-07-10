package ru.example;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

public class User {
    public String login;
    public ArrayList<Animal> animals = new ArrayList<>();
    public String password;
    public User(){}

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void addAnimal(Animal animal){
        animals.add(animal);
    }
}
