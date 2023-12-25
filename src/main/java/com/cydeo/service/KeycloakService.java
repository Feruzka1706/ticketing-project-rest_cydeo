package com.cydeo.service;

import com.cydeo.dto.UserDTO;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;


public interface KeycloakService {

    Response userCreate(UserDTO userDTO);
    void userDelete(String username);

}
