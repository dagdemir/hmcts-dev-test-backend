package org.example.techtask.controller;

import org.example.techtask.model.Task;
import org.example.techtask.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskControllerTest {

    private TaskService taskService;
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        taskService = mock(TaskService.class);
        taskController = new TaskController(taskService);
    }

    @Test
    void testCreateTask() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");

        when(taskService.createTask(task)).thenReturn(task);

        ResponseEntity<Task> response = taskController.createTask(task);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(task, response.getBody());
        verify(taskService, times(1)).createTask(task);
    }

    @Test
    void testGetTask_Found() {
        Task task = new Task();
        task.setId(1L);

        when(taskService.getTaskById(1L)).thenReturn(Optional.of(task));

        ResponseEntity<Task> response = taskController.getTask(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(task, response.getBody());
        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    void testGetTask_NotFound() {
        when(taskService.getTaskById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Task> response = taskController.getTask(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    void testGetAllTasks() {
        Task task1 = new Task();
        Task task2 = new Task();
        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskService.getAllTasks()).thenReturn(tasks);

        List<Task> response = taskController.getAllTasks();

        assertEquals(2, response.size());
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void testUpdateStatus() {
        Task updatedTask = new Task();
        updatedTask.setId(1L);
        updatedTask.setStatus("DONE");

        when(taskService.updateTaskStatus(1L, "DONE")).thenReturn(updatedTask);

        ResponseEntity<Task> response = taskController.updateStatus(1L, "DONE");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("DONE", response.getBody().getStatus());
        verify(taskService, times(1)).updateTaskStatus(1L, "DONE");
    }

    @Test
    void testDeleteTask() {
        doNothing().when(taskService).deleteTask(1L);

        ResponseEntity<Void> response = taskController.deleteTask(1L);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(taskService, times(1)).deleteTask(1L);
    }
}
