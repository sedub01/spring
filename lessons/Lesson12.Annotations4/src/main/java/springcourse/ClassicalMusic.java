package springcourse;

import org.springframework.stereotype.Component;

//@Component("musicBean")
@Component //в таком случае название бина - класс с маленькой буквы
public class ClassicalMusic implements Music{
    @Override
    public String getSong() {
        return "Turkish March";
    }

}
