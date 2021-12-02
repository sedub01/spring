package springcourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

enum Genres{
    ROCK,
    CLASSICAL
}

@Component
public class MusicPlayer {
    private Music music1;
    private Music music2;

    @Autowired
    public MusicPlayer(@Qualifier("rockMusic") Music music1,
                       @Qualifier("classicalMusic") Music music2) {
        this.music1 = music1;
        this.music2 = music2;
    }

    public String playMusic(Genres genre){
        if (genre == Genres.ROCK)
        return "Playing -> " + music1.getSong();
        return "Playing -> " + music2.getSong();
    }
}
