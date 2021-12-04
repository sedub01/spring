package springcourse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class RockMusic implements Music{

    public void doMyInit(){
        System.out.println("Do my init");
    }

    @Override
    public String getSong() {
        return "We will rock you";
    }
}
