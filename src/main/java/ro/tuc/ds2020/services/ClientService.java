package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.LoginDTO;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.Account;
import ro.tuc.ds2020.entities.Client;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.repositories.AccountRepository;
import ro.tuc.ds2020.repositories.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

    public String randomAlphaNumericString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 32;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    public UUID insert(UserDTO userDTO) {
        Account account = new Account(userDTO.getName(), userDTO.getAddress(), userDTO.getBirthDay());
        Account savedAccount = accountRepository.save(account);
        Client client = Client.builder().account(account)
                .password(userDTO.getPassword())
                .username(userDTO.getUsername())
                .token(randomAlphaNumericString())
                .role(userDTO.getRole())
                .build();
        Client savedClient = clientRepository.save(client);
        return savedClient.getId();
    }

    private Account returnAccount(List<Client> clients, UUID id){
        for (Client i: clients) {
            if(i.getId().equals(id)){
                return i.getAccount();
            }
        }
        return null;
    }

    private Client returnClient(List<Client> clients, UUID id){
        for (Client i: clients) {
            if(i.getId().equals(id)){
                return i;
            }
        }
        return null;
    }

    public void delete(UUID clientID) {
        List<Client> clients = clientRepository.findAll();
        Account account = returnAccount(clients,clientID);
        if(account!=null){
            System.out.println(account.getId());
            clientRepository.deleteById(clientID);
            accountRepository.deleteById(account.getId());
        }
    }

    public void update(UUID clientID, UserDTO userDTO) {
        List<Client> clients = clientRepository.findAll();
        Account account = returnAccount(clients, clientID);
        account.setName(userDTO.getName());
        account.setAddress(userDTO.getAddress());
        account.setBirthDay(userDTO.getBirthDay());
        accountRepository.save(account);
        Client client = returnClient(clients, clientID);
        client.setAccount(account);
        client.setPassword(userDTO.getPassword());
        client.setUsername(userDTO.getUsername());
        clientRepository.save(client);
    }

    public UserDTO returnLoginUser(UserDTO userDTO) {
        List<Client> clients = clientRepository.findAll();
        List<Account> accounts = accountRepository.findAll();
        UserBuilder ub = new UserBuilder();
        List<UserDTO> dtos = ub.toUserDTOs(clients,accounts);
        for(UserDTO i: dtos){
            if(i.getPassword().equals(userDTO.getPassword())
            &&i.getUsername().equals(userDTO.getUsername()))
                return i;
        }
        return null;
    }

    public List<UserDTO> findUserDTOs() {
        List<Client> clients = clientRepository.findAll();
        List<Account> accounts = accountRepository.findAll();
        UserBuilder ub = new UserBuilder();
        List<UserDTO> dtos = ub.toUserDTOs(clients,accounts);
        return dtos;
    }

    public UserDTO findUserDTO(UUID clientID) {
        List<Client> clients = clientRepository.findAll();
        List<Account> accounts = accountRepository.findAll();
        UserBuilder ub = new UserBuilder();
        List<UserDTO> dtos = ub.toUserDTOs(clients,accounts);
        for(UserDTO i: dtos){
            if(i.getId().equals(clientID))
                return i;
        }
        return null;
    }
}
