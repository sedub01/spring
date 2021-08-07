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

    private String title, anons, fullText;
    private int views;

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
        return fullText;
    }
    public void setFulltext(String text){
        fullText = text;
    }

    public int getViews(){
        return views;
    }
    public void setViews(int views_){
        views = views_;
    }
}
