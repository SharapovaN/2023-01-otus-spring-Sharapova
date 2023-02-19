package spring.ru.otus.homework.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import spring.ru.otus.homework.config.AppProps;
import spring.ru.otus.homework.model.QuestionDto;
import spring.ru.otus.homework.model.StudentDto;

import java.io.FileNotFoundException;
import java.util.List;

@Service
public class ApplicationRunnerImpl implements ApplicationRunner {

    private final IOService ioService;
    private final StudentService studentService;
    private final QuestionService questionService;
    private final ApplicationStopService applicationStopService;
    private final MessageSource messageSource;
    private final AppProps props;
    private final FileService fileService;

    public ApplicationRunnerImpl(IOService ioService, StudentService studentService, QuestionService questionService,
                                 ApplicationStopService applicationStopService, MessageSource messageSource,
                                 AppProps props, FileService fileService) {
        this.ioService = ioService;
        this.studentService = studentService;
        this.questionService = questionService;
        this.applicationStopService = applicationStopService;
        this.messageSource = messageSource;
        this.props = props;
        this.fileService = fileService;
    }

    @Override
    public void run() {
        while (applicationStopService.isApplicationRunning()) {
            fileService.setUrl(messageSource.getMessage("csv.question.file.url", null, props.getLocale()));

            List<QuestionDto> questions = null;
            try {
                questions = questionService.getQuestions();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            ioService.outputString(messageSource.getMessage("welcome.phrase", null, props.getLocale()));
            StudentDto student = studentService.createStudent();
            ioService.outputString(messageSource.getMessage("test.start.phrase", null, props.getLocale()));

            for (QuestionDto question : questions) {
                questionService.printQuestion(question);
                boolean isAnswerRight = questionService.checkAnswer(ioService.readInt(), question);
                if (isAnswerRight) {
                    studentService.increaseRightAnswersCounter(student);
                }
            }

            ioService.outputString(studentService.getStudentTestingResult(student));
            applicationStopService.stopTesting();
        }
        applicationStopService.shutdownContext();
    }
}
