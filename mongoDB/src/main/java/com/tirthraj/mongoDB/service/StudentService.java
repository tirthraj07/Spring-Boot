package com.tirthraj.mongoDB.service;

import com.tirthraj.mongoDB.entity.Student;
import com.tirthraj.mongoDB.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    // Using Autowired
    @Autowired
    private StudentRepository studentRepository;

    /*
        // Constructor-based Dependency Injection
        private final StudentRepository studentRepository;

        public StudentService(StudentRepository studentRepository){
            this.studentRepository = studentRepository;
        }
    */

    // Create new Student
    public Student createStudent(Student student){
        return studentRepository.save(student);
    }

    // Get all Students
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    // Get student by ID
    public Student getStudentById(String id){
        return studentRepository.findById(id).orElse(null);
    }

    // Update Student
    public Student updateStudent(String id, Student updatedStudent){
        if(studentRepository.existsById((id))){
            updatedStudent.setId(id);
            return studentRepository.save(updatedStudent);
        }

        return null;
    }

    // Delete Student
    public void deleteStudent(String id){
        if(studentRepository.existsById(id)){
            studentRepository.deleteById(id);
        }
    }


}
