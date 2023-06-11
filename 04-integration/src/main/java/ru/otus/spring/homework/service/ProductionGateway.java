package ru.otus.spring.homework.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.homework.model.Task;

@MessagingGateway
public interface ProductionGateway {

    @Gateway(requestChannel = "inputTaskChannel", replyChannel = "outputTaskChannel")
    Task process(Task tasks);

}
