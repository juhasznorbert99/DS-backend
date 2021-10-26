package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.entities.Account;
import ro.tuc.ds2020.entities.Client;

import java.util.ArrayList;
import java.util.List;

public class UserBuilder {
    public UserDTO toUserDTO(Client client, Account account){
        return new UserDTO(client.getId(), client.getUsername(), client.getPassword(), account.getName(), account.getAddress(), account.getBirthDay(), client.getToken(), client.getRole());
    }
    public List<UserDTO> toUserDTOs(List<Client> clients, List<Account> accounts){
        List<UserDTO> dtos = new ArrayList<UserDTO>();
        for(int i=0;i< accounts.size();i++){
            dtos.add(toUserDTO(clients.get(i), accounts.get(i)));
        }
        return dtos;
    }
}
