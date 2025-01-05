package com.tirthraj.lombok.service;

import com.tirthraj.lombok.entity.User;
import com.tirthraj.lombok.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    // Constructor-based Dependency Injection
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    // Services

    public User createUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(ObjectId id){
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUserName(String userName){
        return Optional.ofNullable(userRepository.findByUserName(userName));
    }

    public void deleteUserById(ObjectId id){
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
        }
    }

    public Optional<User> updateUserById(ObjectId id, User user){
        if(userRepository.existsById(id)){
            user.setId(id);
            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }

    public boolean userExists(ObjectId id){
        return userRepository.existsById(id);
    }

    public boolean userNameExists(String userName){
        return userRepository.existsByUserName((userName));
    }

}
