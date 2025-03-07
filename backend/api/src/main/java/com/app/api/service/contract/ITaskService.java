package com.app.api.service.contract;

import com.app.TaskDto;

public interface ITaskService<T> extends CommonOperation<T> {
    void createTask(TaskDto taskDto);
}
