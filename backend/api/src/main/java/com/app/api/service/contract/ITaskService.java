package com.app.api.service.contract;

import org.springframework.security.core.Authentication;

import com.app.api.domain.dto.TaskDto;
import com.app.entities.Task;

public interface ITaskService extends CommonOperation<Task> {
    Task createTask(TaskDto taskDto);
    Task createTask(TaskDto taskDto, Authentication authentication);
}
