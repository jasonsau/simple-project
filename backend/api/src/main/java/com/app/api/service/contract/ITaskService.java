package com.app.api.service.contract;

import com.app.api.domain.dto.TaskDto;
import com.app.entities.Task;

public interface ITaskService<T> extends CommonOperation<T> {
    Task createTask(TaskDto taskDto);
}
