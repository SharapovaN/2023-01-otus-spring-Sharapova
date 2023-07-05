package ru.otus.spring.homework.shell;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final JobLauncher jobLauncher;

    private final Job migrateMongoToPg;

    @SneakyThrows
    @SuppressWarnings("unused")
    @ShellMethod(value = "startMigrationJob", key = "s")
    public void startMigrationJob() {
        JobExecution execution = jobLauncher.run(migrateMongoToPg, new JobParameters());
    }
}
