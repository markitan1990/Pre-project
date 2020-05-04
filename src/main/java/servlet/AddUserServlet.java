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
import java.sql.SQLException;

@WebServlet(value = "/addUser")
public class AddUserServlet extends HttpServlet {
    UserJdbcService userJdbcService = UserJdbcService.getInstance();
    UserHibernateService userHibernateService = UserHibernateService.getInstance();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("name").isEmpty() || req.getParameter("lastName").isEmpty() || req.getParameter("age").isEmpty()) {
            System.out.println("форма не заполнена");
        } else {
            User user = new User(req.getParameter("name"), req.getParameter("lastName"), Long.parseLong(req.getParameter("age")));
            try {
//                userJdbcService.addUser(user);
                userHibernateService.addUser(user);
            } catch (SQLException e) {
                System.out.println("не получилось добавить пользователя");
            }
        }

        getServletContext().getRequestDispatcher("/users").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPut(req, resp);
    }
}
