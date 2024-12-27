package com.service;

import com.dao.TaskDAO;
import com.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskDAO taskDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteTask_Success() {
        long id = 1L;
        Task taskToDelete = new Task();
        taskToDelete.setTaskId(id); 

        when(taskDAO.findById(id)).thenReturn(Optional.of(taskToDelete));
        taskService.deleteTask(id);
        verify(taskDAO, times(1)).delete(taskToDelete); // Ensure delete was called once
    }

    @Test
    void testDeleteTask_NotFound() {
        long id = 999L; 
        when(taskDAO.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            taskService.deleteTask(id);
        });

        assertEquals("No value present", exception.getMessage()); 
        }

    @Test
    void testFindById_Exists() {
        long id = 1L;
        Task task = new Task();
        task.setTaskId(id); 

        when(taskDAO.findById(id)).thenReturn(Optional.of(task));
        boolean exists = taskService.findById(id);
        assertTrue(exists);
    }

    @Test
    void testFindById_NotExists() {
        long id = 999L; 
        when(taskDAO.findById(id)).thenReturn(Optional.empty());
        boolean exists = taskService.findById(id);
        assertFalse(exists);
    }

    @Test
    void testGetTaskById_Success() {
        long id = 1L;
        Task task = new Task();
        task.setTaskId(id); 
        when(taskDAO.findById(id)).thenReturn(Optional.of(task));
        Task result = taskService.getTaskById(id);
        assertNotNull(result);
        assertEquals(id, result.getTaskId()); 
    }

    @Test
    void testGetTaskById_NotFound() {
        long id = 999L; 
        when(taskDAO.findById(id)).thenReturn(Optional.empty());
       Exception exception = assertThrows(NoSuchElementException.class, () -> {
           taskService.getTaskById(id);
       });

       assertEquals("No value present", exception.getMessage()); 
   }
}
