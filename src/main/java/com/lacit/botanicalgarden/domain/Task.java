package com.lacit.botanicalgarden.domain;

import java.util.Date;
import java.util.Objects;

public class Task {

    private Long id;
    private String title;
    private Date date;
    private Plant plant;
    private boolean done;
    private String comment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Task(Long id, String title, Date date, Plant plant, boolean done, String comment) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.plant = plant;
        this.done = done;
        this.comment = comment;
    }


    public Task() {
    }

    public Task(String title, Plant plant, boolean done) {
        this.title = title;
        this.plant = plant;
        this.done = done;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
