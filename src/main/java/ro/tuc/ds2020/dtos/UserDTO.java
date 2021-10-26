package ro.tuc.ds2020.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO{
    private UUID id;
    private String username;
    private String password;
    private String name;
    private String address;
    private Date birthDay;
    private String token;
    private String role;

}
