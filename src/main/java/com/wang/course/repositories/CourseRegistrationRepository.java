package com.wang.course.repositories;

import com.wang.course.models.Course;
import com.wang.course.models.Registration;
import com.wang.course.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> deleteByStudent(Student student);
    List<Registration> deleteByCourse(Course course);
}
