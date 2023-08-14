package com.practice.demo.events;

import com.practice.demo.models.entities.Operation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OperationProceededEvent extends ApplicationEvent {

    private Operation operation;
    private String email;

    public OperationProceededEvent(Object source, Operation operation) {

        super(source);

        this.operation = operation;
        email = operation.getAccount().getClient().getEmail();
    }
}
