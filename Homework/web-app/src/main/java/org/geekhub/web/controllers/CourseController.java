package org.geekhub.web.controllers;

import config.AppConfig;
import models.Course;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import services.CourseService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = CourseController.COURSES_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseController {
    public static final String COURSES_URL = "/courses";

    @GetMapping
    public List<Course> findAllCourse() {
        Optional<CourseService> courseService = getCourseService();
        List<Course> courses = List.of();

        if (courseService.isPresent()) {
            courses = courseService.get().getCourses();
        }

        return courses;
    }

    @GetMapping("/{id}")
    public Course findByIdCourse(@PathVariable int id) {
        Optional<CourseService> courseService = getCourseService();
        Course course = null;

        if (courseService.isPresent()) {
            Optional<Course> courseOptional = courseService.get().getCourse(id);

            if (courseOptional.isPresent()) {
                course = courseOptional.get();
            }
        }

        return course;
    }

    @PostMapping
    public Course saveCourse(@RequestBody Course course) {
        Optional<CourseService> courseService = getCourseService();
        Course createdCourse = null;

        if (courseService.isPresent()) {
            Optional<Course> courseOptional = courseService.get().addCourse(course);
            if (courseOptional.isPresent()) {
                createdCourse = courseOptional.get();
            }

        }

        return createdCourse;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable int id) {
        Optional<CourseService> courseService = getCourseService();
        courseService.ifPresent(service -> service.deleteCourse(id));
    }

    private Optional<CourseService> getCourseService() {
        AnnotationConfigApplicationContext applicationContext =
            new AnnotationConfigApplicationContext(AppConfig.class);
        CourseService courseService = applicationContext.getBean(CourseService.class);

        return Optional.of(courseService);
    }
}
