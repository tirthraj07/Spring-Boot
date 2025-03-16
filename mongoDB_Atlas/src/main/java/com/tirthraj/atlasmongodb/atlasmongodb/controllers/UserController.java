package com.tirthraj.atlasmongodb.atlasmongodb.controllers;

import com.tirthraj.atlasmongodb.atlasmongodb.dto.APIResponse;
import com.tirthraj.atlasmongodb.atlasmongodb.dto.CreateUserRequest;
import com.tirthraj.atlasmongodb.atlasmongodb.dto.UserDTO;
import com.tirthraj.atlasmongodb.atlasmongodb.entity.User;
import com.tirthraj.atlasmongodb.atlasmongodb.service.JournalEntryService;
import com.tirthraj.atlasmongodb.atlasmongodb.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private final UserService userService;
    private final JournalEntryService journalEntryService;
    public UserController(UserService userService, JournalEntryService journalEntryService){
        this.userService = userService;
        this.journalEntryService = journalEntryService;
    }

    // Create
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserRequest userRequest){
        User user = userService.createUser(UserService.convertRequestToUser(userRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserService.convertToDTO(user));
    }

    // Read
    @GetMapping
    public List<UserDTO> getUsers(){
        return userService.getAllUsers().stream().map(UserService::convertToDTO).toList();
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String username){
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));

        return ResponseEntity.ok(UserService.convertToDTO(user));
    }

    // Delete
    @DeleteMapping("/user/{username}")
    public ResponseEntity<APIResponse> deleteUser(@PathVariable String username){
        User user = userService.getUserByUsername(username).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
        userService.deleteUserById(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(true, "User Deleted Successfully"));
    }

}
