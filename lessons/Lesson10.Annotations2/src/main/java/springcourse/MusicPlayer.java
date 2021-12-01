package springcourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MusicPlayer {
//    @Autowired //spring успешно внедрил зависимость
//    private Music music;

    //Внедрение двух зависимостей
    private ClassicalMusic classicalMusic;
    private RockMusic rockMusic;

    @Autowired//внедрение двух зависимостей (не забудь пометить два класса как @Component)
    public MusicPlayer(ClassicalMusic classicalMusic, RockMusic rockMusic) {
        this.classicalMusic = classicalMusic;
        this.rockMusic = rockMusic;
    }

    //    public MusicPlayer(Music music) {
//        this.music = music;
//    }
//
//    @Autowired //все равно на название сеттера, то есть через этот метод необходимо внедрять зависимости
//    public void setfdfgf(Music music){
//        this.music = music;
//    }

    public String playMusic(){
        return "Playing -> " + classicalMusic.getSong();
    }
}
