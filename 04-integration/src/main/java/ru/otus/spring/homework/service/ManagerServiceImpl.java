package ru.otus.spring.homework.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.model.BackendTaskImpl;
import ru.otus.spring.homework.model.FrontendTaskImpl;
import ru.otus.spring.homework.model.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final ProductionGateway productionGateway;

    private static int counter;

    @Override
    public void generateTasks() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        for (int i = 0; i < 5; i++) {
            delay();
            pool.execute(() -> {
                Collection<Task> tasks = generateTaskList();
                System.out.println("New task: " +
                        tasks.stream().map(Task::getDescription)
                                .collect(Collectors.joining(", ")));
                Collection<Task> executedTasks = productionGateway.process(tasks);
                System.out.println("Task execution result: " + executedTasks.stream()
                        .map(t -> t.toString() + "\n")
                        .collect(Collectors.joining(", ")));
            });
        }
    }

    private Task generateTask() {
        if (RandomUtils.nextInt(1, 10) % 2 == 0) {
            return new FrontendTaskImpl("Task N" + counter++ + " Recolor the button");
        } else {
            return new BackendTaskImpl("Task N" + counter++ + " Need a new endpoint");
        }
    }

    private Collection<Task> generateTaskList() {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < RandomUtils.nextInt(1, 5); ++i) {
            tasks.add(generateTask());
        }
        return tasks;
    }

    private void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
