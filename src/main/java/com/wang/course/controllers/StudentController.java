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

import javax.validation.Valid;
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
    public Student create(@Valid @RequestBody Student student) {
        try {
            student.setActive(true);
            Student savedStudent = registerService.studentRepository.saveAndFlush(student);
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
                deletedStudent.setActive(false);
                registerService.studentRepository.save(deletedStudent);
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
     * Retrieve all the students, or the students for a specified course
     *
     * @return
     */
    @ApiOperation("Retrieve all the students, or the students for a specified course")
    @GetMapping("/student")
    public List<Student> search(@RequestParam(value = "course") String course) {
        try {
            List<Student> students = registerService.retrieveStudentForCourse(course);
            return students;
        } catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, getStackTrace(e));
        }
    }
}
