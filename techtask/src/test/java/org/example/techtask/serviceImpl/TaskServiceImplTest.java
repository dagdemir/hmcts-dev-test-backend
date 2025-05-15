package org.example.techtask.serviceImpl;

import org.example.techtask.model.Status;
import org.example.techtask.model.Task;
import org.example.techtask.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    private TaskRepository taskRepository;
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        taskService = new TaskServiceImpl(taskRepository);
    }

    @Test
    void testCreateTask() {
        Task task = new Task();
        task.setTitle("Test Task");

        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.createTask(task);

        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
        verify(taskRepository).save(task);
    }

    @Test
    void testGetTaskById_Found() {
        Task task = new Task();
        task.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getTaskById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(taskRepository).findById(1L);
    }

    @Test
    void testGetTaskById_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Task> result = taskService.getTaskById(1L);

        assertFalse(result.isPresent());
        verify(taskRepository).findById(1L);
    }

    @Test
    void testGetAllTasks() {
        Task task1 = new Task();
        Task task2 = new Task();
        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        verify(taskRepository).findAll();
    }

    @Test
    void testUpdateTaskStatus_Success() {
        Task task = new Task();
        task.setId(1L);
        task.setStatus(Status.IN_PROGRESS);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        Task updated = taskService.updateTaskStatus(1L, "DONE");

        assertEquals(Status.COMPLETED, updated.getStatus());
        verify(taskRepository).findById(1L);
        verify(taskRepository).save(task);
    }

    @Test
    void testUpdateTaskStatus_TaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                taskService.updateTaskStatus(1L, "DONE")
        );

        assertEquals("Task not found", exception.getMessage());
        verify(taskRepository).findById(1L);
        verify(taskRepository, never()).save(any());
    }

    @Test
    void testUpdateTaskStatus_InvalidStatus() {
        Task task = new Task();
        task.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                taskService.updateTaskStatus(1L, "INVALID_STATUS")
        );

        assertTrue(exception.getMessage().contains("No enum constant"));
        verify(taskRepository).findById(1L);
        verify(taskRepository, never()).save(any());
    }

    @Test
    void testUpdateTaskStatus_NullStatus() {
        Task task = new Task();
        task.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                taskService.updateTaskStatus(1L, null)
        );

        verify(taskRepository).findById(1L);
        verify(taskRepository, never()).save(any());
    }

    @Test
    void testUpdateTaskStatus_BlankStatus() {
        Task task = new Task();
        task.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                taskService.updateTaskStatus(1L, "")
        );

        assertTrue(exception.getMessage().contains("No enum constant"));
        verify(taskRepository).findById(1L);
        verify(taskRepository, never()).save(any());
    }

    @Test
    void testDeleteTask() {
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskRepository).deleteById(1L);
    }
}
