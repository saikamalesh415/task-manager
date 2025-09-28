package com.example.task_manager.model;

import java.util.ArrayList;
import java.util.List;

public class Task {
    private Integer id;
    private String title;
    private String description;
    private String deadline; // keep simple as string (dd/mm/yyyy)
    private List<Note> notes = new ArrayList<>();
    private Boolean completed = false;

    public Task() {}

    public Task(Integer id, String title, String description, String deadline, List<Note> notes, Boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        if (notes != null) this.notes = notes;
        this.completed = completed != null ? completed : false;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDeadline() { return deadline; }
    public void setDeadline(String deadline) { this.deadline = deadline; }

    public List<Note> getNotes() { return notes; }
    public void setNotes(List<Note> notes) { this.notes = notes; }

    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }
}
