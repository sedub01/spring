package springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired//так как привязываем бин
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Person> index(){
        //Объект PersonMapper подготавливает человека для запроса
        return jdbcTemplate.query("select * from Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id){
        return jdbcTemplate.query("select * from Person where id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person){
        jdbcTemplate.update("insert into Person values (1, ?, ?, ?);", person.getName(),
                person.getAge(), person.getEmail());
    }

    public void update(int id, Person person){
        jdbcTemplate.update("update Person set name=?, age=?, email=? where id = ?",
                 person.getName(), person.getAge(), person.getEmail(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("delete from Person where id = ?", id);

    }
}
