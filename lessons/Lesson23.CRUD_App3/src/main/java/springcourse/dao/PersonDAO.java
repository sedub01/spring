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
        return people.stream().filter(p -> p.getId() == id).findAny().get();
    }

    public void save(Person person){
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person person){
        Person person1 = show(id);
        person1.setName(person.getName());
    }

    public void delete(int id){
        people.removeIf(p -> p.getId() == id);
    }
}
