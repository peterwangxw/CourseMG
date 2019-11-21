package com.wang.course.controllers;

import com.wang.course.models.Student;
import com.wang.course.services.RegisterService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1")
public class StudentController extends BaseController{

    @Autowired
    private RegisterService registerService;

    /**
     * Create student
     *
     * @param student
     * @return
     */
    @ApiOperation("Create a student")
    @PostMapping("/student")
    public Student create(@RequestBody Student student) {
        try {
            Student savedStudent = registerService.studentRepository.save(student);
            return savedStudent;
        } catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, getStackTrace(e));
        }
    }

    /**
     * Delete student by ID
     *
     * @param id
     * @return
     */
    @ApiOperation("Delete a student by ID")
    @DeleteMapping("/student/{id}")
    public boolean delete(@PathVariable Long id) {
        try {
            Student deletedStudent = registerService.studentRepository.getOne(id);
            if (deletedStudent != null) {
                registerService.studentRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, getStackTrace(e));
        }
    }

    /**
     * Fetch student by ID
     *
     * @param id
     * @return
     */
    @ApiOperation("Retrieve a student by ID")
    @GetMapping("/student/{id}")
    public Student fetch(@PathVariable Long id) {
        try {
            Student student = registerService.studentRepository.getOne(id);
            return student;
        } catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, getStackTrace(e));
        }
    }

    /**
     * Retrieve all the students, or the students for a specified course
     *
     * @return
     */
    @ApiOperation("Retrieve all the students, or the students for a specified course")
    @GetMapping("/student")
    public List<Student> retrieveAll(@RequestParam(value = "course", required = false) String course) {
        try {
            List<Student> students = null;
            if (course == null || course.isEmpty()) {
                // TODO... we may need implement pagination
                students = registerService.studentRepository.findAll(Sort.by(Sort.Direction.ASC, "firstName"));
            } else {
                students = registerService.retrieveStudentForCourse(course);
            }
            return students;
        } catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, getStackTrace(e));
        }
    }
}
