package springcourse.dao;

import org.springframework.stereotype.Component;
import springcourse.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private List<Person> people;
    private static int PEOPLE_COUNT;

    {//блок инициализации
        people = new ArrayList<>();

        people.add(new Person(++PEOPLE_COUNT, "John"));
        people.add(new Person(++PEOPLE_COUNT, "Tom"));
        people.add(new Person(++PEOPLE_COUNT, "Jack"));
        people.add(new Person(++PEOPLE_COUNT, "Mike"));
    }

    public List<Person> index(){
        return people;
    }

    public Person show(int id){
        return people.get(id);
    }
}
