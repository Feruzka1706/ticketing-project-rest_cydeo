package com.cydeo.controller;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user") //all CREATE, GET, UPDATE, DELETE will be under same end point
public class UserController {

   private final UserService userService;

    public UserController( UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> getUsers(){
        List<UserDTO> userDTOList = userService.listAllUsers();

        return ResponseEntity.ok(new ResponseWrapper("Users are successfully retrieved from DB",
                userDTOList, HttpStatus.OK));
    }

    @GetMapping("/{userName}")
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> getUserByUsername(@PathVariable("userName") String userName){

        return ResponseEntity.ok(new ResponseWrapper("User byt username successfully retrieved from DB",
                userService.findByUserName(userName),HttpStatus.OK));
    }


    @PostMapping
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO userDTO){
        userService.save(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).
                body(new ResponseWrapper("User is successfully created",
                HttpStatus.CREATED));
    }

    @PutMapping
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO userDTO){
        userService.update(userDTO);
        return ResponseEntity.ok(new ResponseWrapper("User is successfully updated", userDTO, HttpStatus.OK));
    }

    @DeleteMapping("/{userName}")
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> deleteUserById(@PathVariable("userName") String username){
        userService.deleteByUserName(username);
        return ResponseEntity.ok(new ResponseWrapper("User is by username: "+username+" has been deleted from DB record",
                HttpStatus.OK));

//        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseWrapper("User is successfully deleted",
//                HttpStatus.NO_CONTENT));
        //204 - HttpStatus.NO_CONTENT
         //If above way used no body will be returned in Response body

    }

}
