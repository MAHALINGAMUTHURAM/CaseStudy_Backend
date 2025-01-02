package com.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.model.Task;
import com.service.TaskService; // Assuming you have a service that uses the DAO

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskDAOTest {

    @Mock
    private TaskDAO taskDAO;

    @InjectMocks
    private TaskService taskService; // Assuming you have a service that uses the DAO

    private Task task;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Initialize Task object
        task = new Task();
        task.setTaskId(1L); // Assuming Task has an ID field
        task.setDescription("Sample Task"); // Set description dynamically
    }

    @Test
    public void testSaveTask() {
        // Given
        when(taskDAO.save(any(Task.class))).thenReturn(task);

        // When
        Task savedTask = taskDAO.save(task);

        // Then
        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getTaskId()).isEqualTo(task.getTaskId());
        assertThat(savedTask.getDescription()).isEqualTo(task.getDescription());

        // Verify that the method was called once
        verify(taskDAO, times(1)).save(any(Task.class));
    }

    @Test
    public void testFindById() {
        // Given
        when(taskDAO.findById(task.getTaskId())).thenReturn(Optional.of(task));

        // When
        Optional<Task> foundTask = taskDAO.findById(task.getTaskId());

        // Then
        assertThat(foundTask).isPresent();
        assertThat(foundTask.get().getDescription()).isEqualTo(task.getDescription());

        // Verify that the method was called once
        verify(taskDAO, times(1)).findById(task.getTaskId());
    }

    @Test
    public void testFindById_NoResults() {
        // Given
        when(taskDAO.findById(task.getTaskId())).thenReturn(Optional.empty());

        // When
        Optional<Task> foundTask = taskDAO.findById(task.getTaskId());

        // Then
        assertThat(foundTask).isNotPresent();

        // Verify that the method was called once
        verify(taskDAO, times(1)).findById(task.getTaskId());
    }

    @Test
    public void testFindAll() {
        // Given
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        
        when(taskDAO.findAll()).thenReturn(tasks);

        // When
        List<Task> foundTasks = taskDAO.findAll();

        // Then
        assertThat(foundTasks).hasSize(1);
        assertThat(foundTasks.get(0).getDescription()).isEqualTo(task.getDescription());

        // Verify that the method was called once
        verify(taskDAO, times(1)).findAll();
    }

    @Test
    public void testDeleteTask() {
        // When
        taskDAO.delete(task);

        // Then (no exception should be thrown)

        // Verify that the delete method was called once
        verify(taskDAO, times(1)).delete(task);
    }
}
