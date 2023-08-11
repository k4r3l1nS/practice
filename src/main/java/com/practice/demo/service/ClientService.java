package com.practice.demo.service;

import com.practice.demo.components.event.publishers.OperationProceededPublisher;
import com.practice.demo.dto.entity_dto.ClientDto;
import com.practice.demo.dto.specification_dto.models.ClientSpecificationDto;
import com.practice.demo.dto.paging_and_sotring_dto.models.ClientPagingAndSortingDto;
import com.practice.demo.exceptions.models.ClientAlreadyExistsException;
import com.practice.demo.exceptions.models.EmptyFieldException;
import com.practice.demo.exceptions.models.ResourceNotFoundException;
import com.practice.demo.models.entities.Client;
import com.practice.demo.models.db_views.ClientView;
import com.practice.demo.models.specification.SpecificationBuilder;
import com.practice.demo.repos.entity_repos.ClientRepository;
import com.practice.demo.repos.db_view_repos.ClientViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientViewRepository clientViewRepository;

    @Transactional(readOnly = true)
    public Client findById(Long clientId) {

        return clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client with id = " + clientId + " not found"));
    }


    public ClientView findClientById(Long clientId) {

        return clientRepository.findClientById(clientId);
    }

    public Page<ClientView> fetchNextPage(ClientPagingAndSortingDto pagingAndSortingDto) {

        var pageRequest = pagingAndSortingDto.toPageRequest();

        return clientViewRepository.findAll(pageRequest);
    }

    public Page<ClientView> fetchNextPage(ClientPagingAndSortingDto pagingAndSortingDto,
                                          ClientSpecificationDto clientSpecificationDto) {

        var conditions = clientSpecificationDto.toConditions();

        var specification = new SpecificationBuilder<>().with(conditions).build();
        var pageRequest = pagingAndSortingDto.toPageRequest();

        return clientViewRepository.findAll(specification, pageRequest);
    }

    public void addClient(ClientDto clientDto) throws ClientAlreadyExistsException, EmptyFieldException {

        Objects.requireNonNull(clientDto);

        if (clientDto.hasEmptyFields()) {

            throw new EmptyFieldException("All fields must be filled in");
        }

        if (clientRepository.existsByFirstNameAndLastNameAndBirthDate(clientDto.getFirstName(),
                clientDto.getLastName(), clientDto.getBirthDate())) {

            throw new ClientAlreadyExistsException("Client with these data already exists");
        }

        clientRepository.save(clientDto.toEntity());
    }

    public void updateClient(ClientDto clientDto, Long clientId) {

        var clientEntity = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client with id = " + clientId + " not found"));

        clientDto.mapTo(clientEntity);
    }

    public void activateClientById(Long clientId) {

        var clientEntity = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client with id = " + clientId + " not found"));

        clientEntity.setActive(true);
    }

    public void deactivateClientById(Long clientId) {

        var clientEntity = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client with id = " + clientId + " not found"));

        clientEntity.setActive(false);
    }
}
