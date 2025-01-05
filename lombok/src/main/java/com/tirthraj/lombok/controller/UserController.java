package com.tirthraj.lombok.controller;

import com.tirthraj.lombok.entity.User;
import com.tirthraj.lombok.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    // Constructor based Dependency Injection
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }


    // REST APIs

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<Object> createNewUser(@RequestBody User user){
        try {
            if ((user.getId() == null || !userService.userExists(user.getId())) && !userService.userNameExists(user.getUserName())) {
                User newUser = userService.createUser(user);

                Map<String, Object> response = new HashMap<>() {{
                    put("status", "success");
                    put("message", "User created successfully");
                    put("user", newUser);
                }};
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                Map<String, Object> response = new HashMap<>() {{
                    put("status", "error");
                    put("message", "User with ID " + userService.getUserByUserName(user.getUserName()).get().getId() + " exists already");
                }};
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            Map<String, Object> response = new HashMap<>() {{
                put("status", "error");
                put("message", e.getMessage());
            }};
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PutMapping("/{userName}")
    public ResponseEntity<Object> updateByUserName(@PathVariable String userName, @RequestBody User updatedUser){
        try{
            Optional<User> oldUser = userService.getUserByUserName(userName);
            if(oldUser.isPresent()){
                ObjectId userID = oldUser.get().getId();
                Optional<User> updatedDBUser = userService.updateUserById(userID, updatedUser);
                if(updatedDBUser.isPresent()){
                    Map<String, Object> response = new HashMap<>(){{
                        put("status","success");
                        put("message","user updated successfully");
                        put("user",updatedDBUser.get());
                    }};
                    return ResponseEntity.ok(response);
                }
            }
            Map<String, String> errorResponse = new HashMap<>(){{
                put("status","error");
                put("message","User couldn't update. UserName does not match any");
            }};
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            Map<String, String> errorResponse = new HashMap<>(){{
                put("status","error");
                put("message",e.getMessage());
            }};
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<Map<String,String>> deleteUser(@PathVariable String userName){
        try{
            Optional<User> user = userService.getUserByUserName(userName);
            if(user.isPresent()){
                ObjectId userID = user.get().getId();
                userService.deleteUserById(userID);
                Map<String, String> successResponse = new HashMap<>(){{
                    put("status", "success");
                    put("message", "User deleted successfully");
                }};
                return ResponseEntity.ok(successResponse);
            }

            Map<String, String> errorResponse = new HashMap<>(){{
                put("status", "error");
                put("message", "User with UserName does not exists");
            }};
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            Map<String, String> errorResponse = new HashMap<>(){{
                put("status", "error");
                put("message", e.getMessage());
            }};
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
