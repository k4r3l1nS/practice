package com.practice.demo.components.event.publishers;

import com.practice.demo.dto.entity_dto.OperationDto;
import com.practice.demo.events.OperationProceededEvent;
import com.practice.demo.models.db_views.OperationView;
import com.practice.demo.repos.entity_repos.OperationRepository;
import com.practice.demo.service.ClientService;
import com.practice.demo.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OperationProceededPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final OperationRepository operationRepository;

    public void publishEvent(Long operationId, String contextMessage) {

        System.out.println("Reached publisher");
        var operationView = operationRepository.findViewById(operationId);

        assert(operationView != null);

        applicationEventPublisher.publishEvent(
                new OperationProceededEvent(this, operationView, contextMessage));
    }
}
