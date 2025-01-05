package com.tirthraj.lombok.repository;


import com.tirthraj.lombok.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String userName);
    boolean existsByUserName(String userName);
}
