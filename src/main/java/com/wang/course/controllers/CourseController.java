package com.wang.course.controllers;

import com.wang.course.models.Course;
import com.wang.course.services.RegisterService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1")
public class CourseController extends BaseController{

    @Autowired
    private RegisterService registerService;

    /**
     * Create course
     *
     * @param course
     * @return
     */
    @ApiOperation("Create a course")
    @PostMapping("/course")
    public Course create(@Valid @RequestBody Course course) {
        try {
            course.setActive(true);
            Course savedCourse = registerService.courseRepository.saveAndFlush(course);
            return savedCourse;
        } catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, getStackTrace(e));
        }
    }

    /**
     * Delete course by ID
     *
     * @param id
     * @return
     */
    @ApiOperation("Delete a course by ID")
    @DeleteMapping("/course/{id}")
    public boolean delete(@PathVariable Long id) {
        try {
            Course deletedCourse = registerService.courseRepository.getOne(id);
            if (deletedCourse != null) {
                deletedCourse.setActive(false);
                registerService.courseRepository.saveAndFlush(deletedCourse);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, getStackTrace(e));
        }
    }

    @ApiOperation("Register a course for a student")
    @PutMapping("/register/{courseId}/{studentId}")
    public boolean register(@PathVariable Long courseId, @PathVariable Long studentId) {
        try {
            registerService.register(courseId, studentId);
            return true;
        } catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, getStackTrace(e));
        }
    }
}
