package ru.otus.spring.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.homework.model.Task;

@Service
public class FrontendTaskServiceImpl implements TaskService {
    @Override
    public Task execute(Task task) {
        task.setExecuted(true);
        task.setResult("The button is painted");
        return task;
    }
}
