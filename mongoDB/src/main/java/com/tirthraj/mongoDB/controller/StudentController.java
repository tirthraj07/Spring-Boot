package com.tirthraj.mongoDB.controller;

import com.mongodb.DuplicateKeyException;
import com.tirthraj.mongoDB.entity.Student;
import com.tirthraj.mongoDB.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {

     @Autowired
     private StudentService studentService;

    /*
        // Constructor based Dependency injection

        private final StudentService studentService


        public StudentController(StudentService studentService){
            this.studentService = studentService;
        }
    */

    @GetMapping
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @PostMapping
    public ResponseEntity<Object> createStudent(@RequestBody Student student){
        try{
            Student createdStudent = studentService.createStudent(student);
            Map<String, Object> successResponse = new HashMap<>(){{
                put("status", "success");
                put("message", "Student created successfully");
                put("student", createdStudent);
            }};
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        }
        catch (DuplicateKeyException e) {
            System.out.println("Duplicate key error while creating student: " + e.getMessage());

            Map<String, String> errorResponse = new HashMap<>() {{
                put("status", "error");
                put("message", "A student with the same ID already exists");
            }};
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse); // 409 Conflict
        }
        catch(Exception e){
            System.out.println("Error while creating Student");

            Map<String, String> errorResponse = new HashMap<>(){{
                put("status", "error");
                put("message", "Error while creating student");
            }};
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getStudentByID(@PathVariable String id){
        Student student = studentService.getStudentById(id);
        if(student == null){
            Map<String, String> errorResponse = new HashMap<>(){{
                put("status", "error");
                put("message", "Student with ID: " + id + " not found");
            }};
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        Map<String, Object> successResponse = new HashMap<>(){{
            put("status", "success");
            put("student", student);
        }};

        return ResponseEntity.ok(successResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateStudent(@PathVariable String id, @RequestBody Student updatedStudent){
        Student student = studentService.updateStudent(id, updatedStudent);

        if(student != null){
            Map<String, Object> successResponse = new HashMap<>(){{
                put("status", "success");
                put("message", "Student updated successfully");
                put("student", student);
            }};
            return ResponseEntity.ok(successResponse);
        }

        Map<String, String> errorResponse = new HashMap<>(){{
            put("status","error");
            put("message","Unable to update student");
        }};

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudentByID(@PathVariable String id) {
        try {
            studentService.deleteStudent(id);
            Map<String, String> successResponse = new HashMap<>() {{
                put("status", "success");
                put("message", "Student deleted successfully");
            }};
            return ResponseEntity.ok(successResponse);
        }
        catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>() {{
                put("status", "error");
                put("message", "An unexpected error occurred while deleting the student");
            }};
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
