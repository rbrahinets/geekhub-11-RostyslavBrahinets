package org.geekhub.web.servlets.menu.course;

import exceptions.NotFoundException;
import models.Course;
import org.geekhub.web.servlets.RequestParameter;
import services.CourseService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static org.geekhub.web.servlets.SessionAttributes.ID_SESSION_PARAMETER;

@WebServlet(urlPatterns = "/menu/courses/show-by-id")
public class CoursesShowByIdServlet extends HttpServlet {
    @Override
    protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        showMenu(response);
    }

    @Override
    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        String id = getId(request, response);
        HttpSession session = request.getSession();
        session.setAttribute(ID_SESSION_PARAMETER, id);
        showCourse(id, response);
    }

    private void showMenu(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        try (PrintWriter writer = response.getWriter()) {
            writer.write("<html><head><title>Courses Show By Id</title></head><body>");

            writer.write("<form action=\"show-by-id\" method=\"post\">");
            writer.write("<label for=\"id\">Id: </label>");
            writer.write("<input id=\"id\" type=\"text\" name=\"" + ID_SESSION_PARAMETER + "\">");
            writer.write("<input type=\"submit\" value=\"Show\">");
            writer.write("</form>");

            writer.write("</body></html>");
        }
    }

    private String getId(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        Optional<String> optionalId = Optional.empty();
        try {
            RequestParameter requestParameter = new RequestParameter();
            optionalId = requestParameter.extractParameter(ID_SESSION_PARAMETER, request);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
        return optionalId.orElse("");
    }

    private void showCourse(String id, HttpServletResponse response) throws IOException {
        CourseService courseService = new CourseService();

        response.setContentType("text/html");
        try (PrintWriter writer = response.getWriter()) {
            writer.write("<html><head><title>Courses Show By Id</title></head><body>");
            Optional<Course> course = Optional.empty();

            try {
                if (id.isBlank()) {
                    throw new NotFoundException("Course not found");
                }
                course = courseService.getCourse(Integer.parseInt(id));
            } catch (NotFoundException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            }

            course.ifPresent(
                value -> writer.write(
                    "<h3>"
                        + value.name() + ": "
                        + value.lections() + ", "
                        + value.students()
                        + "</h3>"
                ));

            writer.write("</body></html>");
        }
    }
}