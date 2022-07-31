package com.github.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Пожалуйста, заполни поле")
    @Length(max = 512, message = "Слишком большое сообщение (более 0.5 Мб)")
    private String text;
    @NotBlank(message = "Пожалуйста, введи тег")
    @Length(max = 255, message = "Слишком большой тег (более 0.25 Мб)")
    private String tag;
    @ManyToOne(fetch = FetchType.EAGER) //одному пользователю соотвествует множество сообщений
    @JoinColumn(name = "user_id") //это нужно для того, чтобы в колонке БД было "user_id", а не "author_id"
    // То бишь в таблицу добавляется колонка "user_id" в самом конце
    private User author; //со стороны пользователя это уже OneToMany

    private String filename; //картинка хранится на жестком диске

    public Message() {}

    public Message(String text, String tag, User user) {
        this.author = user;
        this.text = text;
        this.tag = tag;
    }

    public String getAuthorName(){
        return author != null ? author.getUsername() : "<none>";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
