package com.app.api.service.implement;

import com.app.api.domain.dto.TaskDto;
import com.app.api.repository.TaskRepository;
import com.app.api.service.contract.ITaskService;
import com.app.api.service.contract.IUserService;
import com.app.entities.Task;
import com.app.entities.User;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {
    private final TaskRepository repository;
    private final IUserService iUserService;

    @Override
    public Page<Task> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Task> findAll(Pageable pageable, Specification<Task> specification) {
        return repository.findAll(specification, pageable);
    }

    @Override
    public void save(Task task) {
        repository.save(task);
    }

    @Override
    public void delete(Task task) {
        repository.delete(task);
    }

    @Override
    public Task findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    public Task createTask(TaskDto taskDto) {
        Task task = new Task();
        BeanUtils.copyProperties(taskDto, task);
        repository.save(task);
        return task;
    }

    public Task createTask(TaskDto taskDto, Authentication authentication) {
        User user = iUserService.findByEmail(authentication.getName());
        if(user == null) return null;
        Task task = new Task();
        task.setCompleted(false);
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setUser(user);
        repository.save(task);
        return task;
    }

    public Task updateTask(Task task, TaskDto taskDto) {
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        repository.save(task);
        return task;
    }
}
