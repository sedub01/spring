package com.sedub01.blog.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Post {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title, anons, full_text;
    private int views;
    public Post(){}
    public Post(String title_, String anons_, String full_text_){
        title = title_;
        anons = anons_;
        full_text = full_text_;
    }

    public long getId(){
        return id;
    }
    public void setId(long id_){
        id = id_;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title_){
        title = title_;
    }

    public String getAnons(){
        return anons;
    }
    public void setAnons(String anons_){
        anons = anons_;
    }

    public String getFulltext(){
        return full_text;
    }
    public void setFulltext(String text){
        full_text = text;
    }

    public int getViews(){
        return views;
    }
    public void setViews(int views_){
        views = views_;
    }
}
