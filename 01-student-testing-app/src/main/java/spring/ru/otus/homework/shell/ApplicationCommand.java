package spring.ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import spring.ru.otus.homework.config.AppProps;
import spring.ru.otus.homework.model.StudentDto;
import spring.ru.otus.homework.service.StudentService;
import spring.ru.otus.homework.service.TestService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommand {

    private final StudentService studentService;
    private final MessageSource messageSource;
    private final AppProps props;
    private final TestService testService;
    private StudentDto student;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login() {
        this.student = studentService.createStudent();
        return messageSource.getMessage("welcome.phrase",
                new String[]{student.getName(), student.getSurname()}, props.getLocale());
    }

    @ShellMethod(value = "Test command", key = "test")
    @ShellMethodAvailability(value = "isStudentAuthorized")
    public String runTest() {
        return testService.runTest(student);
    }

    @ShellMethod(value = "Exit command", key = "logout")
    @ShellMethodAvailability(value = "isStudentAuthorized")
    public String logout() {
        this.student = null;
        return messageSource.getMessage("end.phrase", null, props.getLocale());
    }

    private Availability isStudentAuthorized() {
        return student == null ? Availability.unavailable(messageSource.getMessage("login.phrase", null, props.getLocale()))
                : Availability.available();
    }
}
