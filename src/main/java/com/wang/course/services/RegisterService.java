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
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
        String hql = "select s " +
                "from Student s, Registration r, Course c " +
                "where s.id = r.student.id and r.course.id = c.id and c.name = :name " +
                "and s.isActive = true and r.isActive = true and c.isActive = true " +
                "order by s.firstName";
        TypedQuery<Student> query = entityManager.createQuery(hql, Student.class);
        query.setParameter("name", course);
        List<Student> students = query.getResultList();

        return students;
    }

    public void register(long courseId, long studentId) throws EntityNotFoundException {
        Course course = null;
        Student student = null;

        try {
            course = courseRepository.getOne(courseId);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Course isn't found");
        }

        try {
            student = studentRepository.getOne(studentId);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Student isn't found");
        }

        Registration registration = new Registration();
        registration.setActive(true);
        registration.setCourse(course);
        registration.setStudent(student);

        courseRegistrationRepository.save(registration);
    }

}
