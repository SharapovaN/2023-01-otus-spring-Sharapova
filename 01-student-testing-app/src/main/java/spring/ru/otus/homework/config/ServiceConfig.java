package spring.ru.otus.homework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import spring.ru.otus.homework.service.ApplicationRunner;
import spring.ru.otus.homework.service.ApplicationStopSettings;
import spring.ru.otus.homework.service.ApplicationStopService;
import spring.ru.otus.homework.service.ApplicationStopServiceImpl;
import spring.ru.otus.homework.service.ApplicationStopServiceSettingsProvider;
import spring.ru.otus.homework.service.CsvFileServiceImpl;
import spring.ru.otus.homework.service.FileService;
import spring.ru.otus.homework.service.IOConsoleService;
import spring.ru.otus.homework.service.IOService;
import spring.ru.otus.homework.service.QuestionService;
import spring.ru.otus.homework.service.QuestionServiceImpl;
import spring.ru.otus.homework.service.StudentService;
import spring.ru.otus.homework.service.StudentServiceImpl;

import java.io.FileNotFoundException;

@Configuration
@PropertySource("classpath:application.properties")
public class ServiceConfig {

    @Bean
    public FileService fileService(@Value("${csv.question.file.url}") String url, QuestionService questionService) throws FileNotFoundException {
        FileService fileService = new CsvFileServiceImpl(url, questionService);
        fileService.init();
        return fileService;
    }

    @Bean
    public IOService ioService() {
        return new IOConsoleService(System.out, System.in);
    }

    @Bean
    public QuestionService questionService(IOService ioService) {
        return new QuestionServiceImpl(ioService);
    }

    @Bean
    public StudentService studentService(IOService ioService) {
        return new StudentServiceImpl(ioService);
    }

    @Bean
    public ApplicationStopService applicationStopService(IOService ioService,
                                                         ApplicationStopServiceSettingsProvider settingsProvider,
                                                         @Value("${application.execution.flag}") boolean executingFlag) {
        return new ApplicationStopServiceImpl(ioService, settingsProvider, executingFlag);
    }

    @Bean
    public ApplicationStopServiceSettingsProvider applicationStopServiceSettingsProvider(@Value("${application.confirm.exit.flag}")
                                                                                         boolean confirmExit) {
        return new ApplicationStopSettings(confirmExit);
    }

    @Bean
    public ApplicationRunner applicationRunner(IOService ioService, StudentService studentService,
                                               QuestionService questionService, ApplicationStopService applicationStopService) {
        return new ApplicationRunner(ioService, studentService, questionService, applicationStopService);
    }
}
