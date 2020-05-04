package servlet;

import model.User;
import service.UserHibernateService;
import service.UserJdbcService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/editUser")
public class EditUserServlet extends HttpServlet {
    UserJdbcService userJdbcService = UserJdbcService.getInstance();
    UserHibernateService userHibernateService = UserHibernateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("name").isEmpty() || req.getParameter("lastName").isEmpty() || req.getParameter("age").isEmpty()) {
            System.out.println("форма не заполнена");
        } else {
            long id = Long.parseLong(req.getParameter("id"));
            String newName = req.getParameter("name");
            String newLastName = req.getParameter("lastName");
            long newAge = Long.parseLong(req.getParameter("age"));
            User user = new User(id, newName, newLastName, newAge);
//            userJdbcService.editUser(user);
            userHibernateService.editUser(user);
        }
        getServletContext().getRequestDispatcher("/users").forward(req, resp);
    }
}
