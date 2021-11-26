package springcourse;

public class JazzMusic implements Music{
    private JazzMusic() {}

    public static JazzMusic getJazzMusic(){
        return new JazzMusic();
    }

    public void doInit(){
        System.out.println("I'm doing initialization");
    }

    //для бинов со scope prototype Spring не вызывает destroy методы
    public void doMyDestroy(){
        System.out.println("I'm destroying");
    }

    @Override
    public String getSong() {
        return "Hit the Road Jack";
    }
}
