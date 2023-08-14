package com.practice.demo.components.event.publishers;

import com.practice.demo.events.OperationProceededEvent;
import com.practice.demo.models.entities.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OperationProceededPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(Operation operation) {

        applicationEventPublisher.publishEvent(
                new OperationProceededEvent(this, operation));
    }
}
