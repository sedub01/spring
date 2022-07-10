package ru.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Menu {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public Menu(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final Scanner scanner = new Scanner(System.in);
    private List<User> users = new ArrayList<>();
    private User currentUser;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private int loginAttempts = 0;

    public void exe(){
        int choice = -1;
        String login, password;
        users = jdbcTemplate.query("select user from users", new UserMapper());
        while (choice != 0){
            System.out.println("Who are you? (type 0 to exit)");
            System.out.println("1. Unauthenticated user");
            System.out.println("2. Authenticated user");
            System.out.print("Enter your choice: ");
            choice = checkChoice(0, 2);
            if (choice == 1){
                System.out.println("What do you want?");
                System.out.println("1. Check username");
                System.out.println("2. Sign up");
                System.out.println("0. Go back");
                System.out.print("Enter your choice: ");
                choice = checkChoice(0, 2);
                if (choice == 1){
                    System.out.print("Put login your want to check: ");
                    login = scanner.nextLine();
                    if (isLoginAvailable(login))
                        System.out.println("Login is available!");
                }
                else if (choice == 2){
                    System.out.println("You need to sign up: put your login and password");
                    do {
                        System.out.print("Login: ");
                        login = scanner.nextLine();
                    } while (!isLoginAvailable(login));
                    System.out.print("Password: ");
                    password = scanner.nextLine();
                    //Подтверждение логина и пароля
                    System.out.println("Repeat login and password: ");
                    System.out.print("Login: ");
                    String newLogin = scanner.nextLine();
                    System.out.print("Password: ");
                    String newPassword = scanner.nextLine();
                    if (login.equals(newLogin) && password.equals(newPassword)) {
                        //занести изменения в БД и список
                        currentUser = new User(login, password);
                        users.add(currentUser);
                        try {
                            jdbcTemplate.update("insert into users values (?, ?)",
                                    login,
                                        objectMapper.writeValueAsString(currentUser));
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        System.out.println("You're in!");
                        AnimalsMenu animalsMenu = new AnimalsMenu(jdbcTemplate, currentUser);
                        animalsMenu.exe();
                    }
                    else{
                        loginAttempts++;
                        new JsonError(1, "Wrong login or password");
                    }
                }
                choice = -1;
            }
            else if (choice == 2){
                System.out.print("Login: ");
                login = scanner.nextLine();
                System.out.print("Password: ");
                password = scanner.nextLine();
                boolean flag = false;
                boolean isEntryAllowed = true;
                for (User u : users)
                    if (u.login.equals(login))
                        if (u.password.equals(password)){
                            String query = "select ifnull(timediff(sysdate()," +
                                    "lastTimeAttempt), '01:00:01') > '01:00:00' from animals.users " +
                                    "where login = ?";
                            isEntryAllowed = jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
                                boolean i;
                                i = rs.getBoolean(1);
                                return i;
                            }, new Object[]{login});
                            if (isEntryAllowed){
                                flag = true;
                                System.out.println("You're in");
                                loginAttempts = 0;
                            }
                        }
                if (flag && isEntryAllowed){
                    for (User user : users)
                        if (user.login.equals(login))
                            currentUser = user;
                    AnimalsMenu animalsMenu = new AnimalsMenu(jdbcTemplate, currentUser);
                    animalsMenu.exe();
                }
                else{
                    loginAttempts++;
                    if (isEntryAllowed) new JsonError(1, "Wrong login or password");
                    if (loginAttempts >= 10){
                        System.out.println("It's forbidden for " + login + " sign in for 1 hour!");

                        jdbcTemplate.update("update users set lastTimeAttempt = sysdate() where login = ?",
                                login);
                    }
                }
            }
        }
    }

    public int checkChoice(int min, int max){
        return checkChoice(min, max, scanner);
    }

    static int checkChoice(int min, int max, Scanner scanner) {
        int choice = -1;
        while (choice < min || choice > max){
            choice = scanner.nextInt();
            if (choice < min || choice > max){
                new JsonError(2, "Wrong option");
                System.out.print("Try again: ");
            }
        }
        scanner.nextLine(); //для чтения строки после числа
        return choice;
    }

    public boolean isLoginAvailable(String login){
        for (User u : users)
            if (u.login.equals(login)) {
                System.out.println("There is already such a login in the database");
                return false;
            }
        return true;
    }
}
