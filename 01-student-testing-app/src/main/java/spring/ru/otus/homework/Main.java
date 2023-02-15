package spring.ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import spring.ru.otus.homework.service.ApplicationRunnerImpl;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.run(Main.class, args);
        ApplicationRunnerImpl applicationRunner = context.getBean(ApplicationRunnerImpl.class);

        applicationRunner.run();
    }
}
