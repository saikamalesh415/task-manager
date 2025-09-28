package com.example.task_manager.repository;

import com.example.task_manager.model.Task;
import java.util.*;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepository {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private int nextId = 1;

    // CREATE
    public Task addTask(Task task) {
        task.setId(nextId++);
        tasks.put(task.getId(), task);
        return task;
    }

    // READ (all tasks)
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    // READ (one task by id)
    public Optional<Task> getTaskById(int id) {
        return Optional.ofNullable(tasks.get(id));
    }

    // UPDATE
    public Task updateTask(int id, Task updatedTask) {
        if (!tasks.containsKey(id)) {
            return null;
        }
        updatedTask.setId(id);
        tasks.put(id, updatedTask);
        return updatedTask;
    }

    // DELETE
    public boolean deleteTask(int id) {
        return tasks.remove(id) != null;
    }
}
