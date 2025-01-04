## Step 1: Create a Model (DAO)

The models are generally stored in package called `entity`

Example : `entity/Student.java`  :

```java
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
    // ...
}
```

## Step 2: Create a Repository

A **repository** is a specialized interface that provides an abstraction for interacting with a data store (like a database). It allows developers to perform CRUD (Create, Read, Update, Delete) operations and query the database without writing boilerplate code.

Spring Data provides built-in repository interfaces (like `MongoRepository`, `JpaRepository`, etc.) to make data access simple, efficient, and declarative.

It is a mechanism to encapsulate the data access logic

Repositories are generally stored in a package `repository`  

Example : `repository/StudentRepository.java`

```java
public interface StudentRepository extends MongoRepository<Student, String> {
    // You can define custom query methods here if needed
}
```

## Step 3: Create a Service

The repository interface is injected into the service layer to perform database operations.

This can be done in two ways

1. Using `@Autowired`
```java
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;


}
```

2. Using `Constructor-based dependency injection`
```java
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

}
```


Create methods inside of the service to perform operations on the repository

```java
    // Create new Student
    public void createStudent(Student student){
        studentRepository.save(student);
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
```


## Step 4: Create a Rest Controller

Inject the `StudentService` in the controller to use the business logic for Student APIs

Example : Using `@Autowired`   
```java
@RestController
@RequestMapping("/students")
public class StudentController {
    // Using Autowired 
    @Autowired
    private StudentService studentService;
    
}
```
  
Example : Using **Constructor based Dependency Injection**  
```java
@RestController
@RequestMapping("/students")
public class StudentController {
    // Constructor based Dependency injection
    private final StudentService studentService;
            
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }
    
}
```

Add the REST API Mappings

```java
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

```
