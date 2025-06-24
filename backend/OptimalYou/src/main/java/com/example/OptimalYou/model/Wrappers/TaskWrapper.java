package com.example.OptimalYou.model.Wrappers;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TaskWrapper {
    private int id;
    private String username;
    private String taskTitle;
    private String taskDescription;
    private int priority;
    private Date issuedDate;
    private Date deadline;
    private boolean completed;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "TaskWrapper{" +
                "completed=" + completed +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", taskTitle='" + taskTitle + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", priority=" + priority +
                ", issuedDate=" + issuedDate +
                ", deadline=" + deadline +
                '}';
    }
}
