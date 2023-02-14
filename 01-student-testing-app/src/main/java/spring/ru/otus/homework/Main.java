package spring.ru.otus.homework;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import spring.ru.otus.homework.service.ApplicationRunnerImpl;

@ComponentScan
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class);
        ApplicationRunnerImpl applicationRunner = context.getBean(ApplicationRunnerImpl.class);

        applicationRunner.run();
    }
}
