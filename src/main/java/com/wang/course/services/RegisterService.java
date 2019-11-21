package com.wang.course.services;

import com.wang.course.models.Course;
import com.wang.course.models.Registration;
import com.wang.course.models.Student;
import com.wang.course.repositories.CourseRegistrationRepository;
import com.wang.course.repositories.CourseRepository;
import com.wang.course.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalTime;
import java.util.List;

/**
 * The business manager
 */
@Service
public class RegisterService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public StudentRepository studentRepository;

    @Autowired
    public CourseRepository courseRepository;

    @Autowired
    public CourseRegistrationRepository courseRegistrationRepository;

    public RegisterService() {}

    public List<Student> retrieveStudentForCourse(String course) {
        // TODO... we should prevent SQL injection here
        String sql = "select s from Student s, Registration r, Course c where s.id = r.student_id and r.course_id = c.id and c.name = '%s'";
        TypedQuery<Student> query = entityManager.createQuery(String.format(sql, course), Student.class);
        List<Student> students = query.getResultList();

        return students;
    }

    public void register(long courseId, long studentId) throws Exception {
        Course course = courseRepository.getOne(courseId);
        if (course == null) {
            throw new Exception("Course isn't found");
        }

        Student student = studentRepository.getOne(studentId);
        if (course == null) {
            throw new Exception("Student isn't found");
        }

        Registration registration = new Registration();
        registration.setActive(true);
        registration.setCourse(course);
        registration.setStudent(student);

        courseRegistrationRepository.save(registration);
    }

}
