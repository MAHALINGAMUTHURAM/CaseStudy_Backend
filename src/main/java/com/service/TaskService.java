package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.TaskDAO;
import com.model.Task;

@Service
public class TaskService {
	
	@Autowired
	TaskDAO taskDAO;
	
	public void deleteTask(long id)
	{
		Task task=taskDAO.findById(id).get();
		taskDAO.delete(task);
	}
	
    public boolean findById(long id) {
        return taskDAO.findById(id).isPresent();
    }
    
    public Task getTaskById(long id)
    {
    	return taskDAO.findById(id).get();
    }

}
