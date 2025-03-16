package com.tirthraj.atlasmongodb.atlasmongodb.service;

import com.tirthraj.atlasmongodb.atlasmongodb.dto.CreateUserRequest;
import com.tirthraj.atlasmongodb.atlasmongodb.dto.UserDTO;
import com.tirthraj.atlasmongodb.atlasmongodb.entity.User;
import com.tirthraj.atlasmongodb.atlasmongodb.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public static User convertRequestToUser(CreateUserRequest userRequest){
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setJournalEntries(new ArrayList<>());
        return user;
    }

    public static UserDTO convertToDTO(User user){
        return new UserDTO(
                user.getId().toString(),
                user.getUsername(),
                user.getJournalEntries() != null ? user.getJournalEntries().size() : 0
        );
    }

    // Create


    public User createUser(User user){
        return userRepository.save(user);
    }

    // Read
    public Optional<User> getUserById(ObjectId id){
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    // Update
    public Optional<User> updateUserById(ObjectId id, User user){
        if(userRepository.existsById(id)){
            user.setId(id);
            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }

    // Delete
    public void deleteUserById(ObjectId id){
        if(userRepository.existsById(id))
            userRepository.deleteById(id);
    }


}
