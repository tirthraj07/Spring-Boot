package com.tirthraj.restAPI.controller;

import com.tirthraj.restAPI.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final Map<Long, Student> studentMap = new HashMap<>();

    // GET /students -> List of all students
    @GetMapping
    public List<Student> getStudents(){
        return new ArrayList<>(studentMap.values());
    }

    // POST /student -> Add a new student
    @PostMapping
    public ResponseEntity<Object> addStudent(@RequestBody Student student){
        if(studentMap.containsKey(student.getStudentID())){
            Map<String, String> errorResponse = new HashMap<>(){{
                put("status","error");
                put("message","Student with this ID already exists.");
            }};
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

        studentMap.put(student.getStudentID(), student);

        Map<String, Object> successResponse = new HashMap<>(){{
            put("status","success");
            put("message","Student added successfully");
            put("student", student);
        }};

        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    // GET /students/{id} -> Get a student by ID or if not found {"status": "error", "message": "student not found"}
    @GetMapping("/{id}")
    public ResponseEntity<Object> getStudent(@PathVariable long id){
        Student student = studentMap.get(id);
        if(student == null){
            Map<String, String> errorResponse = new HashMap<>(){{
                put("status", "error");
                put("message", "student not found");
            }};
            return ResponseEntity.status(404).body(errorResponse);
        }
        return ResponseEntity.ok(student);
    }

    // DELETE /students/{id} -> Delete a student by ID or if not found {"status": "error", "message":"student not found"}
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable long id){
        Map<String, String> response = new HashMap<>(){{
            put("status","success");
            put("message","student deleted successfully");
        }};

        if(studentMap.containsKey(id)){
            studentMap.remove(id);
            return ResponseEntity.ok(response);
        }

        response.put("status", "error");
        response.put("message", "student not found");
        return ResponseEntity.status(404).body(response);
    }

    // PUT /students/{id} -> Update an existing student (replace entire resource)
    @PutMapping("/{id}")
    public ResponseEntity<Object> createOrUpdateStudent(@PathVariable long id, @RequestBody Student student){
        Map<String, Object> response = new HashMap<>(){{
            put("status", "success");
            put("student", student);
        }};

        HttpStatus status;

        if(studentMap.containsKey(id)){
            // UPDATE QUERY
            studentMap.put(student.getStudentID(), student);
            response.put("message", "Student infomation updated");
            status = HttpStatus.OK;
        }
        else{
            // INSERT QUERY
            studentMap.put(student.getStudentID(), student);
            response.put("message", "New Student entity created");
            status = HttpStatus.CREATED;
        }


        return ResponseEntity.status(status).body(response);

    }

    // PATCH /students/{id} -> Update partial information of a student
    @PatchMapping("/{id}")
    public ResponseEntity<Object> patchStudent(@PathVariable long id, @RequestBody Map<String, String> updates) {
        Student student = studentMap.get(id);
        if (student == null) {
            Map<String, String> errorResponse = new HashMap<>(){{
                put("status","error");
                put("message","Student not found");
            }};
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        // Update only the fields present in the request
        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    student.setName(value);
                    break;
                case "contactNumber":
                    student.setContactNumber(value);
                    break;
                default:
                    break;
            }
        });

        Map<String, Object> response = new HashMap<>(){{
            put("status", "success");
            put("message", "Student (partially) updated successfully");
            put("student", student);
        }};

        return ResponseEntity.ok(response);
    }

    // Route that uses Query Parameters and Path Parameters
    // Example: POST /students/search/{id}?includeContact=true
    @PostMapping("/search/{id}")
    public ResponseEntity<Object> searchStudent(
            @PathVariable long id,
            @RequestParam boolean includeContact) {

        Student student = studentMap.get(id);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", student.getStudentID());
        response.put("name", student.getName());

        if (includeContact) {
            response.put("contactNumber", student.getContactNumber());
        }

        return ResponseEntity.ok(response);
    }



}
