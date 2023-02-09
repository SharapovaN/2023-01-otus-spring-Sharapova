package spring.ru.otus.homework;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.ru.otus.homework.model.QuestionDto;
import spring.ru.otus.homework.service.FileService;
import spring.ru.otus.homework.service.QuestionService;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        FileService fileService = context.getBean(FileService.class);
        QuestionService questionService = context.getBean(QuestionService.class);

        String[] strings = fileService.parseCSVToStringArray();

        for (String string : strings) {
            QuestionDto question = questionService.createQuestion(string);

            questionService.printQuestion(question);
        }

    }
}
