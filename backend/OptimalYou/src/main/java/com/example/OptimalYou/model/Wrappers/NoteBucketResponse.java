package com.example.OptimalYou.model.Wrappers;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoteBucketResponse {
    private int id;
    private String title;
    private List<NoteWrapper> notes;
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<NoteWrapper> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteWrapper> notes) {
        this.notes = notes;
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
        return "NoteBucketResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", notes=" + notes +
                ", username='" + username + '\'' +
                '}';
    }
}
