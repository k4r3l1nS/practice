package com.practice.demo;

import com.practice.demo.dto.entity_dto.ClientDto;
import com.practice.demo.events.OperationProceededEvent;
import com.practice.demo.service.ClientService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.time.LocalDate;

@SpringBootTest
@RecordApplicationEvents
class ClientServiceFullContextTest {

    @Autowired
    private ClientService clientService;

    @Test
    void test(@NotNull @Autowired ApplicationEvents applicationEvents) {

        clientService.addClient(ClientDto.builder()
                        .firstName("d").lastName("d").birthDate(LocalDate.now()).email("1@1.ru")
                .build());

        applicationEvents.stream(OperationProceededEvent.class).forEach(operationProceededEvent ->
                System.out.println(":: " + operationProceededEvent.getOperation().getId()));
        System.out.println(applicationEvents.stream(OperationProceededEvent.class).count());
    }
}
