package ru.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AnimalsMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JdbcTemplate jdbcTemplate;
    private final User currentUser;
    private final List<Animal> animals; //Это просто превдоним для экономии места
    private final List<String> kinds = Arrays.asList("cat", "dog", "wolf", "mouse", "horse", "snake");

    public AnimalsMenu(JdbcTemplate jdbcTemplate, User currentUser) {
        this.jdbcTemplate = jdbcTemplate;
        this.currentUser = currentUser;
        animals = currentUser.animals;
    }

    public void exe(){
        int choice, id, idCounter = animals.get(animals.size() - 1).id + 1;
        String name, kind, birthDate, sex;
        Animal animal;
        boolean flag;
        do{
            System.out.println("What do you want?");
            System.out.println("1. Create/edit/delete your animals");
            System.out.println("2. Get list of your animals");
            System.out.println("3. Get info about animal with ID");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            choice = checkChoice(0, 3);
            if (choice == 1){
                System.out.print("Available kinds: ");
                for (String k : kinds)
                    System.out.print(k + " | ");
                System.out.println();
                System.out.println("1. Create animal");
                System.out.println("2. Edit animal");
                System.out.println("3. Delete animal");
                System.out.print("Enter your choice: ");
                choice = checkChoice(1, 3);
                if (choice == 1){
                    name = getName();
                    kind = getKind();
                    System.out.print("Enter the birthdate: ");
                    birthDate = scanner.nextLine();
                    sex = getSex();
                    animal = new Animal(idCounter++, name, kind, birthDate, sex);
                    currentUser.addAnimal(animal);
                    updateDB(currentUser);
                }
                else if (choice == 2){
                    System.out.print("Enter the name of this animal: ");
                    name = scanner.nextLine();
                    if (!isAnimalFound(name))
                        new JsonError(101, "Animal not found");
                    else{
                        System.out.println("The animal found");
                        animal = new Animal();
                        for (Animal a : animals)
                            if (name.equals(a.name))
                                animal = a;
                        System.out.println("Enter new parameters");
                        name = getName();
                        kind = getKind();
                        System.out.print("Enter the birthdate: ");
                        birthDate = scanner.nextLine();
                        sex = getSex();
                        //animal.id = idCounter++;
                        animal.setName(name);
                        animal.setKind(kind);
                        animal.setBirth_date(birthDate);
                        animal.setSex(sex);
                        updateDB(currentUser);
                    }
                }
                else if (choice == 3){
                    System.out.print("Enter the name of this animal: ");
                    name = scanner.nextLine();
                    if (!isAnimalFound(name))
                        new JsonError(101, "Animal not found");
                    else{
                        for (int i = 0; i < animals.size(); i++)
                            if (animals.get(i).name.equals(name)){
                                animals.remove(i);
                                break;
                            }
                        updateDB(currentUser);
                    }
                }
            }
            else if (choice == 2){
                System.out.println();
                for (Animal a : animals){
                    printInfo(a);
                    System.out.println("---------");
                }
            }
            else if (choice == 3){
                System.out.print("Enter ID of your animal: ");
                id = scanner.nextInt();
                scanner.nextLine();
                flag = false;
                for (Animal a : animals)
                    if (id == a.id){
                        flag = true;
                        printInfo(a);
                    }
                if (!flag) new JsonError(101, "Animal not found");

            }
        }while (choice != 0);

    }

    public int checkChoice(int min, int max){
        return Menu.checkChoice(min, max, scanner);
    }

    public String getName(){
        boolean flag;
        String name;
        do{
            flag = true;
            System.out.print("Enter the name: ");
            name = scanner.nextLine();
            for (Animal a : animals)
                if (name.equals(a.name)){
                    new JsonError(102, "Animal with this name already exists");
                    flag = false;
                }
        }while (!flag);
        return name;
    }

    public String getKind(){
        boolean flag;
        String kind;
        do {
            flag = false;
            System.out.print("Enter the kind: ");
            kind = scanner.nextLine();
            for (String k : kinds)
                if (k.equals(kind)){
                    flag = true;
                    break;
                }
            if (!flag) new JsonError(103, "Incorrect kind");
        }while (!flag);
        return kind;
    }

    public String getSex(){
        boolean flag;
        String sex;
        do {
            flag = false;
            System.out.print("Enter the sex: ");
            sex = scanner.nextLine();
            if (!sex.equals("m") && !sex.equals("f"))
                new JsonError(105, "Sex should be only \"m\" or \"f\"");
            else flag = true;
        }while (!flag);
        return sex;
    }

    public boolean isAnimalFound(String name){
        for (Animal a : animals)
            if (a.name.equals(name))
                return true;
        return false;
    }

    public void updateDB(User currentUser){
        try {
            jdbcTemplate.update("update users set user = ? where login = ?",
                    objectMapper.writeValueAsString(currentUser),
                    currentUser.login);
            System.out.println("Data renewed");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void printInfo(Animal a){
        System.out.println("id: " + a.id);
        System.out.println("name: " + a.name);
        System.out.println("kind: " + a.kind);
        System.out.println("birthdate: " + a.birth_date);
        System.out.println("sex: " + a.sex);
    }
}
