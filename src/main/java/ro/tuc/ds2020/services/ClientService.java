package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.entities.Account;
import ro.tuc.ds2020.entities.Client;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.repositories.AccountRepository;
import ro.tuc.ds2020.repositories.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

    private final AccountRepository accountRepository;

    private final ClientRepository clientRepository;

    public ClientService(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll() {
        List<Client> accounts = clientRepository.findAll();
        return accounts;
    }

    public UUID insert(UserDTO userDTO) {
        Account account = new Account(userDTO.getName(), userDTO.getAddress(), userDTO.getBirthDay());
        Account savedAccount = accountRepository.save(account);
        Client client = Client.builder().account(account)
                .password(userDTO.getPassword())
                .username(userDTO.getUsername())
                .token(userDTO.getToken())
                .role(userDTO.getRole())
                .devices(new ArrayList<Device>())
                .build();
        Client savedClient = clientRepository.save(client);
        return savedClient.getId();
    }
}
