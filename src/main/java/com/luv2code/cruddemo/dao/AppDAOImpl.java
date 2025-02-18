package com.luv2code.cruddemo.dao;

import com.luv2code.cruddemo.entity.Course;
import com.luv2code.cruddemo.entity.Instructor;
import com.luv2code.cruddemo.entity.InstructorDetail;
import com.luv2code.cruddemo.entity.Review;
import com.luv2code.cruddemo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO {

    // define field for entity manager
    private EntityManager entityManager;

    private static final Logger logger = LoggerFactory.getLogger(AppDAOImpl.class);

    // inject entity manager using constructor injection
    @Autowired
    public AppDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Instructor theInstructor) {
        entityManager.persist(theInstructor);
    }

    @Override
    @Transactional
    public void save(Course theCourse) {

        entityManager.persist(theCourse);
    }

    @Override
    @Transactional
    public void save(Student student) {

        entityManager.persist(student);
    }

    @Override
    public Instructor findInstructorById(int theId) {
        return entityManager.find(Instructor.class, theId);
    }

    @Override
    public Course findCourseById(int theId) {

        Course theCourse = entityManager.find(Course.class, theId);
        return theCourse;
    }

    @Override
    public Review findReviewById(int theId) {

        return entityManager.find(Review.class, theId);
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int theId) {

        TypedQuery<Instructor> query = entityManager.createQuery(
                "select i from Instructor i " +
                        "JOIN FETCH i.courses " +
                        "JOIN FETCH i.instructorDetail " +
                        "where i.id=:data", Instructor.class);
        query.setParameter("data", theId);

        return query.getSingleResult();

    }

    @Override
    @Transactional
    public void update(Instructor theInstructor) {
        entityManager.merge(theInstructor);
    }

    @Override
    @Transactional
    public void update(Course theCourse) {
        entityManager.merge(theCourse);
    }

    @Override
    @Transactional
    public void deleteInstructorById(int theId) {

        // retrieve the instructor
        Instructor tempInstructor = entityManager.find(Instructor.class, theId);

        // remove the associated object reference
        for (Course course : tempInstructor.getCourses()) {
            course.setInstructor(null);
        }

        // delete the instructor
        entityManager.remove(tempInstructor);
    }

    @Override
    @Transactional
    public void deleteCourseById(int theId) {

        Course tempCourse = entityManager.find(Course.class, theId);

        if (tempCourse != null) {
            entityManager.remove(tempCourse);
            System.out.println("Course deleted");
        } else {
            System.out.println("Course not found");
        }
    }

    @Override
    @Transactional
    public void deleteReviewById(int theId) {

        Review tempReview = entityManager.find(Review.class, theId);
        if (tempReview != null) {
            // Debug level: Log additional details before the deletion if needed.
            logger.debug("Preparing to delete review: {}", tempReview);

            entityManager.remove(tempReview);

            // Info level: Log the successful deletion as a normal operational message.
            logger.info("Deleted review: {}", tempReview);
        } else {
            // Warn level: Log a warning because not finding the review is unexpected.
            logger.warn("Review not found with id: {}", theId);
        }

    }

    @Override
    public InstructorDetail findInstructorDetailById(int theId) {
        return entityManager.find(InstructorDetail.class, theId);
    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int theId) {

        // retrieve instructor detail
        InstructorDetail tempInstructorDetail = entityManager.find(InstructorDetail.class, theId);

        // remove the associated object reference
        // break bi-directional link
        //
        tempInstructorDetail.getInstructor().setInstructorDetail(null);

        // delete the instructor detail
        entityManager.remove(tempInstructorDetail);
    }

    @Override
    public List<Course> findCourses() {

        TypedQuery<Course> query = entityManager.createQuery(
                "from Course", Course.class);

        List<Course> courses = query.getResultList();

        return courses;
    }

    @Override
    public Course findCourseByIdWithStudents(int courseId) {

        return entityManager.createQuery(
                        "select c from Course c " +
                                "LEFT JOIN FETCH c.students " +
                                "WHERE c.id=:courseId", Course.class)
                .setParameter("courseId", courseId)
                .getSingleResult();
    }

    @Override
    public List<Course> findAllCoursesInclStudents() {

        TypedQuery<Course> query = entityManager.createQuery(
                "select c from Course c LEFT JOIN FETCH c.students", Course.class);

        List<Course> courses = query.getResultList();

        return courses;
    }

    @Override
    public List<Course> findCoursesByInstructorId(int id) {

        TypedQuery<Course> query = entityManager.createQuery(
                "from Course c where c.instructor.id=:data", Course.class);
        query.setParameter("data", id);

        List<Course> courses = query.getResultList();

        return courses;
    }

    @Override
    public Course findCourseByIdJoinFetch(int courseId) {

        TypedQuery<Course> query = entityManager.createQuery(
                "SELECT c FROM Course c " +
                        "JOIN FETCH c.reviews " +
                        "WHERE c.id=:data", Course.class);

        query.setParameter("data", courseId);

        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void deleteStudentById(int theId) {
        Student tempStudent = entityManager.find(Student.class, theId);

        if (tempStudent == null) {
            logger.warn("Student with ID {} not found, no deletion performed.", theId);
            return;
        }

        // Convert to array to avoid concurrent modification issues
        List<Course> courses = new ArrayList<>(tempStudent.getCourses());
        for (Course course : courses) {
            course.getStudents().remove(tempStudent);
        }

        entityManager.remove(tempStudent);
        logger.info("Student with ID {} deleted successfully.", theId);
    }

    @Override
    public Student findStudentById(int studentId) {

        return entityManager.find(Student.class, studentId);
    }
}







