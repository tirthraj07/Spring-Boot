package com.tirthraj.mongoDB.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "students")
public class Student {
    @Id
    private String id;  // This will map to MongoDB's _id field

    /*
        In MongoDB, the _id field is a mandatory and unique identifier for each document in a collection.
        If you don't explicitly provide an _id field when inserting a document, MongoDB will automatically generate one
        The @Id annotation in Spring Data is used to mark the field in a Java class that will be mapped to MongoDB's _id field.
        The field annotated with @Id becomes the unique identifier for the document in your Java model.
        If you don’t explicitly set a value for the @Id field, Spring Data will let MongoDB generate an _id automatically (like an ObjectId).
     */

    private String name;
    private int age;
    private String contactNumber;

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
