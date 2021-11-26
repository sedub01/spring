package springcourse;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml"
        );

        JazzMusic jazzMusic = context.getBean("musicBean", JazzMusic.class);
        System.out.println(jazzMusic.getSong());
//        MusicPlayer firstMusicPlayer = context.getBean("musicPlayer", MusicPlayer.class);
//        MusicPlayer secondMusicPlayer = context.getBean("musicPlayer", MusicPlayer.class);
//
//        System.out.println(firstMusicPlayer == secondMusicPlayer);
//        firstMusicPlayer.playMusic();
//        secondMusicPlayer.setVolume(20);
//        System.out.println(firstMusicPlayer.getName());
//        System.out.println(firstMusicPlayer.getVolume());
        context.close();
    }
}
