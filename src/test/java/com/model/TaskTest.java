package com.model;
 
import static org.junit.jupiter.api.Assertions.*;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
 
class TaskTest {
 
    private Task task;
 
    @BeforeEach
    void setUp() {
        task = new Task();
    }
 
    @Test
    void testSetAndGetTaskId() {
        long taskId = 1L;
        task.setTaskId(taskId);
        assertEquals(taskId, task.getTaskId(), "Task ID should be set and retrieved correctly");
    }
 
    @Test
    void testSetAndGetDescription() {
        String description = "Clean the room";
        task.setDescription(description);
        assertEquals(description, task.getDescription(), "Description should be set and retrieved correctly");
    }
 
    @Test
    void testSetAndGetRoom() {
        Room room = new Room();
        room.setRoomId(1L);
 
        task.setRoom(room);
        assertNotNull(task.getRoom(), "Room should not be null after being set");
        assertEquals(room, task.getRoom(), "Room should be set and retrieved correctly");
    }
 
    @Test
    void testTaskInitialization() {
        assertNull(task.getDescription(), "Description should be null initially");
        assertNull(task.getRoom(), "Room should be null initially");
    }
}