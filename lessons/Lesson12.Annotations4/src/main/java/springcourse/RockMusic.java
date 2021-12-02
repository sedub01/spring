package springcourse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype") // каждый раз создаются разные объекты
public class RockMusic implements Music{

    public void doMyInit(){
        System.out.println("Do my init");
    }

    @Override
    public String getSong() {
        return "We will rock you";
    }
}
