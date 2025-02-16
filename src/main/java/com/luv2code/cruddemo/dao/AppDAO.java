package com.luv2code.cruddemo.dao;

import com.luv2code.cruddemo.entity.Course;
import com.luv2code.cruddemo.entity.Instructor;
import com.luv2code.cruddemo.entity.InstructorDetail;
import com.luv2code.cruddemo.entity.Review;
import com.luv2code.cruddemo.entity.Student;

import java.util.List;

public interface AppDAO {

    void save(Instructor theInstructor);

    void save(Course theCourse);

    void save(Student student);

    Instructor findInstructorById(int theId);

    Course findCourseById(int theId);

    Review findReviewById(int theId);

    Instructor findInstructorByIdJoinFetch(int theId);

    void update(Instructor theInstructor);

    void update(Course theCourse);

    void deleteInstructorById(int theId);

    void deleteCourseById(int theId);

    void deleteReviewById(int theId);

    InstructorDetail findInstructorDetailById(int theId);

    void deleteInstructorDetailById(int theId);

    List<Course> findCourses();

    Course findCourseByIdWithStudents(int courseId);

    List<Course> findAllCoursesInclStudents();

    List<Course> findCoursesByInstructorId(int id);

    Course findCourseByIdJoinFetch(int courseId);
}













