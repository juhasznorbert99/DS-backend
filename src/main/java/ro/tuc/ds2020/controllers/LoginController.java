package ro.tuc.ds2020.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.LoginDTO;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.entities.Client;
import ro.tuc.ds2020.services.AccountService;
import ro.tuc.ds2020.services.ClientService;

@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginController {

    private final AccountService accountService;
    private final ClientService clientService;

    public LoginController(AccountService accountService, ClientService clientService) {
        this.accountService = accountService;
        this.clientService = clientService;
    }

    @PostMapping()
    public ResponseEntity<UserDTO> getUser(@RequestBody UserDTO userDTO){
        UserDTO returnedUser = clientService.returnLoginUser(userDTO);
        return new ResponseEntity<>(returnedUser, HttpStatus.OK);
    }

}
