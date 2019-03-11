package com.lacit.botanicalgarden.repos;

import com.lacit.botanicalgarden.domain.Task;

import java.util.List;

public interface TaskRepo {

    List<Task> findAll();
    Task findOne(Long id);
    Long save(Task task);
    int update(Task task);
    int delete(Long id);
}
