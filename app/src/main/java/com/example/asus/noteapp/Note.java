package com.example.asus.noteapp;

import java.io.Serializable;

/**
 * Created by Asus on 7/7/2017.
 */

public class Note implements Serializable {
    private int id;
    private String title;
    private String content;

    public Note() {
    }

    public Note(String title, String content) {
        this.setTitle(title);
        this.setContent(content);
    }

    public Note(int id, String title, String content) {
        this.setId(id);
        this.setTitle(title);
        this.setContent(content);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return (getTitle());
    }
}
