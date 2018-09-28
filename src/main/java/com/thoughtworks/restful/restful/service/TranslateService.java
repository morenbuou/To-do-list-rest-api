package com.thoughtworks.restful.restful.service;

import com.thoughtworks.restful.restful.client.TranslateClient;
import com.thoughtworks.restful.restful.model.Todo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TranslateService {
    @Autowired
    TranslateClient translateClient;

    @Retryable(maxAttempts = 2, backoff = @Backoff(1))
    public Todo translate(Todo todo) {
        log.info("translate try");
        todo.setTranslation(translateClient.getTranslate(todo.getName()));
        return todo;
    }

    @Recover
    public Todo recover (RuntimeException e, Todo todo) {
        todo.setTranslation("N/A");
        return todo;
    }
}
