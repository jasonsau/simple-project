package com.app.api.controller;

import com.app.TaskDto;
import com.app.api.service.contract.ITaskService;
import com.app.entities.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    public final ITaskService taskService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Task>> getAllTasks() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Task> tasks = taskService.findAll(pageable);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createTask(@RequestBody TaskDto taskDto) {
        try {
            taskService.createTask(taskDto);
            return ResponseEntity.ok("Task created successfully");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity.badRequest().body("Task could not be created");
        }
    }

    @DeleteMapping(value = "/{task}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteTask(@PathVariable Task task) {
        try {
            taskService.delete(task);
            return ResponseEntity.ok("Task deleted successfully");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity.badRequest().body("Task could not be deleted");
        }
    }
}
