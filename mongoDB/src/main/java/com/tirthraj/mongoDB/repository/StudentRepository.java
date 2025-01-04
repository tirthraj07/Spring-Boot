package com.tirthraj.mongoDB.repository;

import com.tirthraj.mongoDB.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, String> {
    // You can define custom query methods here if needed
}
