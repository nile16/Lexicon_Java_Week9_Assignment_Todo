package se.lexicon.model;

import java.time.LocalDate;

public class Todo {
    private final int id;
    private String title;
    private String description;
    private LocalDate deadline;
    private Boolean done;
    private Integer assigneeId;

    public Todo(String title, String description, LocalDate deadline, Boolean done, Integer assigneeId) {
        this.id = 0;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.done = done;
        this.assigneeId = assigneeId;
    }

    public Todo(int todoId, String title, String description, LocalDate deadline, Boolean done, Integer assigneeId) {
        this.id = todoId;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.done = done;
        this.assigneeId = assigneeId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Integer getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Integer assigneeId) {
        this.assigneeId = assigneeId;
    }

    @Override
    public String toString() {
        return "Todo{ " +
                "todoId=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", done=" + done +
                ", assigneeId=" + assigneeId +
                " }";
    }
}
