package ro.tuc.ds2020.controllers;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.Account;
import ro.tuc.ds2020.entities.Client;
import ro.tuc.ds2020.services.AccountService;
import ro.tuc.ds2020.services.ClientService;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/user")
public class UserController {
    private final AccountService accountService;
    private final ClientService clientService;

    @Autowired
    public UserController(AccountService accountService, ClientService clientService){
        this.accountService=accountService;
        this.clientService=clientService;
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getClients(){
        List<Client> clients = clientService.findAll();
        List<Account> accounts = accountService.findAll();
        UserBuilder ub = new UserBuilder();
        List<UserDTO> dtos = ub.toUserDTOs(clients,accounts);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UUID> createClient(@RequestBody UserDTO userDTO){
        UUID savedUserID = clientService.insert(userDTO);
        return new ResponseEntity<>(savedUserID, HttpStatus.CREATED);
    }



}
