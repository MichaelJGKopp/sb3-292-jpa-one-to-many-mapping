package com.luv2code.cruddemo;

import com.luv2code.cruddemo.dao.AppDAO;
import com.luv2code.cruddemo.entity.Course;
import com.luv2code.cruddemo.entity.Instructor;
import com.luv2code.cruddemo.entity.InstructorDetail;
import com.luv2code.cruddemo.entity.Review;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO) {

		return runner -> {
			// createInstructor(appDAO);

			// findInstructor(appDAO);

			// deleteInstructor(appDAO);

			// findInstructorDetail(appDAO);

			// deleteInstructorDetail(appDAO);

			// createInstructorWithCourses(appDAO);

			// findInstructorWithCourses(appDAO);

			// findCoursesForInstructor(appDAO);

			// findInstructorWithCoursesJoinFetch(appDAO);

			// updateInstructor(appDAO);

			// updateCourse(appDAO);

			// deleteCourse(appDAO);

			// createInstructorWithCoursesAndReviews(appDAO);

			 // deleteReview(appDAO);

			// findCourseById(appDAO);

			 findCourseByIdJoinFetch(appDAO);
		};
	}

	private void findCourseByIdJoinFetch(AppDAO appDAO) {

		int courseId = 11;
		Course course = appDAO.findCourseByIdJoinFetch(courseId);
		System.out.println("Course with id: " + courseId + " is: " + course + "\n");

		if (course != null) {
			System.out.println("REVIEWS\n"
					+ "=".repeat(30) + "\n"
					+ course.getReviews());
		}
	}

	private void findCourseById(AppDAO appDAO) {

		int courseId = 11;
		Course course = appDAO.findCourseById(courseId);
		System.out.println("Course with id: " + courseId + " is: " + course + "\n");

		if (course != null) {
			System.out.println("REVIEWS\n"
					+ "=".repeat(30) + "\n"
					+ course.getReviews());
		}
	}

	private void deleteReview(AppDAO appDAO) {

		int reviewId = 4;
		System.out.println("Deleting review id: " + reviewId);

		appDAO.deleteReviewById(reviewId);

		System.out.println("Review with id: " + reviewId
				+ (appDAO.findReviewById(reviewId) == null ? " deleted!" : " not deleted!"));
	}

	private void deleteCourse(AppDAO appDAO) {

		int courseId = 10;
		System.out.println("Deleting course id: " + courseId);

		appDAO.deleteCourseById(courseId);

		System.out.println("Course with id: " + courseId
				+ (appDAO.findCourseById(courseId) == null ? " deleted!" : " not deleted!"));
	}

	private void updateInstructor(AppDAO appDAO) {

		int theId = 1;
		System.out.println("Finding instructor id: " + theId);
		Instructor tempInstructor = appDAO.findInstructorById(theId);

		System.out.println("Updating instructor id: " + theId);
		tempInstructor.setLastName("DOE");
		appDAO.update(tempInstructor);

		System.out.println("tempInstructor: " + tempInstructor);
	}

	private void updateCourse(AppDAO appDAO) {

		int theId = 10;
		System.out.println("Find course by id: " + theId);
		Course tempCourse = appDAO.findCourseById(theId);

		System.out.println("Updating course: " + tempCourse);
		tempCourse.setTitle("DCI course");
		appDAO.update(tempCourse);

		System.out.println("Updated course: " + tempCourse);
	}

	private void findInstructorWithCoursesJoinFetch(AppDAO appDAO) {

		int id = 1;
		System.out.println("Finding instructor id: " + id);
		Instructor instructor = appDAO.findInstructorByIdJoinFetch(id);
		System.out.println("Found instructor: " + instructor);

		System.out.println("Get associated courses: " + instructor.getCourses());

		System.out.println("Done!");
	}

	private void findCoursesForInstructor(AppDAO appDAO) {

		int id = 1;
		// find the instructor
		System.out.println("Finding instructor id: " + id);
		Instructor instructor = appDAO.findInstructorById(id);

		System.out.println("Found instructor: " + instructor);

		// set courses for the instructor from db
		List<Course> courses = appDAO.findCoursesByInstructorId(id);

		// associate the objects
		instructor.setCourses(courses);

		System.out.println("Get associated courses: " + instructor.getCourses());

		System.out.println("Done!");
	}

	private void findInstructorWithCourses(AppDAO appDAO) {

		int id = 1;
		System.out.println("Finding instructor id: " + id);
		Instructor instructor = appDAO.findInstructorById(id);
		System.out.println("Found instructor: " + instructor);

		System.out.println("Get associated courses: " + instructor.getCourses());

		System.out.println("Done!");
	}

	private void createInstructorWithCourses(AppDAO appDAO) {

		// create the instructor
		Instructor tempInstructor =
				new Instructor("Susan", "Public", "susan.public@luv2code.com");

		// create the instructor detail
		InstructorDetail tempInstructorDetail =
				new InstructorDetail(
						"http://www.luv2code.com/youtube",
						"Video Games");

		// associate the objects
		tempInstructor.setInstructorDetail(tempInstructorDetail);

		// add some courses
		for (Course course : new Course[] {
				new Course("python"),
				new Course("java")
		}) {
			tempInstructor.addCourse(course);
		}

		// save the instructor and will cascade save the courses
		System.out.println("Saving instructor: " + tempInstructor);
		appDAO.save(tempInstructor);
		System.out.println("Saved instructor: " + tempInstructor);
	}

	private void createInstructorWithCoursesAndReviews(AppDAO appDAO) {

		// create the instructor
		Instructor tempInstructor =
				new Instructor("Susan", "Public", "susan.public@luv2code.com");

		// create the instructor detail
		InstructorDetail tempInstructorDetail =
				new InstructorDetail(
						"http://www.luv2code.com/youtube",
						"Video Games");

		// associate the objects
		tempInstructor.setInstructorDetail(tempInstructorDetail);

		// add some courses
		for (Course course : new Course[] {
				new Course("python"),
				new Course("java")
		}) {
			tempInstructor.addCourse(course);

			for (Review review : new Review[] {
					new Review("Great course!"),
					new Review("Complex course!"),
			}) {
				course.addReview(review);
			}
		}

		// save the instructor and will cascade save the courses
		System.out.println("Saving instructor: " + tempInstructor);
		appDAO.save(tempInstructor);
		System.out.println("Saved instructor: " + tempInstructor);
	}

	private void deleteInstructorDetail(AppDAO appDAO) {

		int theId = 3;
		System.out.println("Deleting instructor detail id: " + theId);

		appDAO.deleteInstructorDetailById(theId);

		System.out.println("Done!");
	}

	private void findInstructorDetail(AppDAO appDAO) {

		// get the instructor detail object
		int theId = 2;
		InstructorDetail tempInstructorDetail = appDAO.findInstructorDetailById(theId);

		// print the instructor detail
		System.out.println("tempInstructorDetail: " + tempInstructorDetail);

		// print the associated instructor
		System.out.println("the associated instructor: " + tempInstructorDetail.getInstructor());

		System.out.println("Done!");
	}

	private void deleteInstructor(AppDAO appDAO) {

		int theId = 1;
		System.out.println("Deleting instructor id: " + theId);

		appDAO.deleteInstructorById(theId);

		System.out.println("Done!");
	}

	private void findInstructor(AppDAO appDAO) {

		int theId = 2;
		System.out.println("Finding instructor id: " + theId);

		Instructor tempInstructor = appDAO.findInstructorById(theId);

		System.out.println("tempInstructor: " + tempInstructor);
		System.out.println("the associated instructorDetail only: " + tempInstructor.getInstructorDetail());

	}

	private void createInstructor(AppDAO appDAO) {

		/*
		// create the instructor
		Instructor tempInstructor =
				new Instructor("Chad", "Darby", "darby@luv2code.com");

		// create the instructor detail
		InstructorDetail tempInstructorDetail =
				new InstructorDetail(
						"http://www.luv2code.com/youtube",
						"Luv 2 code!!!");
		*/

		// create the instructor
		Instructor tempInstructor =
				new Instructor("Madhu", "Patel", "madhu@luv2code.com");

		// create the instructor detail
		InstructorDetail tempInstructorDetail =
				new InstructorDetail(
						"http://www.luv2code.com/youtube",
						"Guitar");

		// associate the objects
		tempInstructor.setInstructorDetail(tempInstructorDetail);

		// save the instructor
		//
		// NOTE: this will ALSO save the details object
		// because of CascadeType.ALL
		//
		System.out.println("Saving instructor: " + tempInstructor);
		appDAO.save(tempInstructor);

		System.out.println("Done!");
	}
}








