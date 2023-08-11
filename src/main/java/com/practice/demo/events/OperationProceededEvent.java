package com.practice.demo.events;

import com.practice.demo.models.db_views.OperationView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OperationProceededEvent extends ApplicationEvent {

    private String contextMessage;
    private OperationView operationView;
    private String email;

    public OperationProceededEvent(Object source, OperationView operationView, String contextMessage) {

        super(source);

        this.operationView = operationView;
        this.contextMessage = contextMessage;
    }
}
