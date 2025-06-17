package com.example.OptimalYou.model.Wrappers;

import com.example.OptimalYou.model.User;
import jakarta.persistence.ManyToOne;

import java.util.Date;

public class NoteWrapper {
    private int id;
    private String title;
    private Date issuedDate;
    private String note;
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
        return "NoteWrapper{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", issuedDate=" + issuedDate +
                ", note='" + note + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
