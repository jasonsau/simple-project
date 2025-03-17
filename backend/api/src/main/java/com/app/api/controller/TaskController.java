package com.app.api.controller;

import com.app.api.config.specification.TaskSpecification;
import com.app.api.domain.dto.TaskDto;
import com.app.api.service.contract.ITaskService;
import com.app.api.service.contract.IUserService;
import com.app.entities.Task;
import com.app.entities.User;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController extends Controller{

    private final ITaskService taskService;
    private final IUserService iUserService;


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Task>> getAllTasks(Authentication authentication) {
        User user = iUserService.findByEmail(authentication.getName());
        Specification<Task> taskSpecification = TaskSpecification.byUser(user);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Task> tasks = taskService.findAll(pageable, taskSpecification);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createTask(@Validated @RequestBody TaskDto taskDto, Authentication authentication) {
        try {
            taskDto.setCompleted(false);
            return ResponseEntity.ok(taskService.createTask(taskDto, authentication));
        }catch (Exception exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity.badRequest().body(
                message.error("Task could not created", "400")
            );
        }
    }

    @DeleteMapping(value = "/{task}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteTask(@PathVariable Task task) {
        try {
            taskService.delete(task);
            return ResponseEntity.ok(message.ok("Task deleted"));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity.badRequest().body(
                message.error("Task could not created", "400")
            );
        }
    }

    @PutMapping(path ="/{task}")
    public ResponseEntity<?> updateTask(@PathVariable Task task, @Validated @RequestBody TaskDto taskDto, Authentication authentication) {
        try {
            Task updatedTask = taskService.updateTask(task, taskDto);
            return ResponseEntity.ok(updatedTask);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity.badRequest().body(
                message.error("Task could not created", "400")
            );
        }
    }
}
