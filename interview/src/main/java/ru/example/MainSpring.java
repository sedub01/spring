package ru.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainSpring {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                SpringConfig.class
        );
        Menu menu = context.getBean("menu", Menu.class);
        menu.exe();
        context.close();
    }
}
