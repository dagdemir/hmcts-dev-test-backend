package org.example.techtask.service;

import org.example.techtask.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Task createTask(Task task);

    Optional<Task> getTaskById(Long id);

    List<Task> getAllTasks();

    Task updateTaskStatus(Long id, String status);

    void deleteTask(Long id);
}
