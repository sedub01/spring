package springcourse.dao;

import org.springframework.stereotype.Component;
import springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;

    private static final String URL = "jdbc:mysql://localhost:3306/first_db";
    private static final String username = "root";
    private static final String password = "123456";

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person> index(){
        List<Person> people = new ArrayList<>();

        //хранит в себе sql запрос
        try {
            Statement statement = connection.createStatement();
            String SQL = "select * from Person";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()){
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));

                people.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return people;
    }

    public Person show(int id){
        //return people.stream().filter(p -> p.getId() == id).findAny().get();
        return null;
    }

    public void save(Person person){
//        person.setId(++PEOPLE_COUNT);
//        people.add(person);
        try {
            Statement statement = connection.createStatement();
            String SQL = "insert into Person values(" + 1 + ", '" + person.getName() + "', "+
                    person.getAge() + ", '" + person.getEmail() + "')";

            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, Person person){
//        Person person1 = show(id);
//        person1.setName(person.getName());
//        person1.setAge(person.getAge());
//        person1.setEmail(person.getEmail());
    }

    public void delete(int id){
        //people.removeIf(p -> p.getId() == id);
    }
}
