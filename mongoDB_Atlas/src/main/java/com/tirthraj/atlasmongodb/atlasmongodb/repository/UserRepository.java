package com.tirthraj.atlasmongodb.atlasmongodb.repository;

import com.tirthraj.atlasmongodb.atlasmongodb.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
