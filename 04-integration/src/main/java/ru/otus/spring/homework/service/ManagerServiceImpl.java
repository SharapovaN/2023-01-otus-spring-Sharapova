package ru.otus.spring.homework.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.model.BackendTaskImpl;
import ru.otus.spring.homework.model.FrontendTaskImpl;
import ru.otus.spring.homework.model.Task;

@Service
@AllArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final ProductionGateway productionGateway;

    private static int counter;

    @Override
    public void generateTasks() {
        for (int i = 0; i < 10; i++) {
            delay();
            Task newTask = generateTask();
            System.out.println("New task: " + newTask.getDescription());
            Task executedTask = productionGateway.process(newTask);
            System.out.println("Task execution result: " + executedTask.toString());
        }
    }

    private Task generateTask() {
        if (RandomUtils.nextInt(1, 10) % 2 == 0) {
            return new FrontendTaskImpl("Task N" + counter++ + " Recolor the button");
        } else {
            return new BackendTaskImpl("Task N" + counter++ + " Need a new endpoint");
        }
    }

    private void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
