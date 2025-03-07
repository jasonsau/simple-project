package com.app.api.service.implement;

import com.app.TaskDto;
import com.app.api.repository.TaskRepository;
import com.app.api.service.contract.ITaskService;
import com.app.entities.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService<Task> {
    private final TaskRepository repository;

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

    public void createTask(TaskDto taskDto) {
        Task task = new Task();
        BeanUtils.copyProperties(taskDto, task);
        repository.save(task);
    }
}
