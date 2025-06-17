package com.example.OptimalYou.model.Wrappers;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class NoteBucketWrapper {
    private int id;
    private String title;
    private String username;
    private List<Integer> note_ids;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getNote_ids() {
        return note_ids;
    }

    public void setNote_ids(List<Integer> note_ids) {
        this.note_ids = note_ids;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "NoteBucketWrapper{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", username='" + username + '\'' +
                ", note_ids=" + note_ids +
                '}';
    }
}
