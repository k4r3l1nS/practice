package com.practice.demo.service;

import com.practice.demo.dto.ClientDto;
import com.practice.demo.dto.specification.models.ClientSpecificationDto;
import com.practice.demo.dto.paging_and_sotring.ClientPagingAndSortingDto;
import com.practice.demo.exceptions.model.ClientAlreadyExistsException;
import com.practice.demo.models.Client;
import com.practice.demo.models.db_views.ClientView;
import com.practice.demo.models.specification.SpecificationBuilder;
import com.practice.demo.repos.AccountRepository;
import com.practice.demo.repos.ClientRepository;
import com.practice.demo.repos.db_view_repos.ClientViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final ClientViewRepository clientViewRepository;

    @Transactional(readOnly = true)
    public Client findById(Long clientId) {

        return clientRepository.findById(clientId).orElseThrow();
    }


    public ClientView findClientById(Long clientId) {

        return clientRepository.findClientById(clientId);
    }

    public List<ClientView> findAllClients() {

        return clientRepository.getAllClients();
    }

    public Page<Client> fetchNextPage(Pageable pageable) {

        return clientRepository.findAll(pageable);
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

    public void addClient(ClientDto clientDto) {

        Objects.requireNonNull(clientDto);

        if (clientRepository.findClientByFirstNameAndLastNameAndBirthDate(clientDto.getFirstName(),
                clientDto.getLastName(), clientDto.getBirthDate()) != null) {

            throw new ClientAlreadyExistsException("Client with these data already exists");
        }

        var client = clientDto.toEntity();
        clientRepository.save(client);
    }

    public void updateClient(ClientDto clientDto, Long clientId) {

        var clientEntity = clientRepository.findById(clientId).orElseThrow();

        clientDto.mapTo(clientEntity);
    }

    public void activateClientById(Long clientId) {

        var clientEntity = clientRepository.findById(clientId).orElseThrow();

        clientEntity.setActive(true);
    }

    public void deactivateClientById(Long clientId) {

        var clientEntity = clientRepository.findById(clientId).orElseThrow();

        clientEntity.setActive(false);
    }
}
