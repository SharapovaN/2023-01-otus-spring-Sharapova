package ru.otus.spring.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.homework.model.Task;

@Service
public class BackendTaskServiceImpl implements TaskService {
    @Override
    public Task execute(Task task) {
        task.setExecuted(true);
        task.setResult("The endpoint is created");
        return task;
    }
}
