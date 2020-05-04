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
import java.util.List;

@WebServlet(value = "/users")
public class UserServlet extends HttpServlet {
    UserJdbcService userJdbcService = UserJdbcService.getInstance();
    UserHibernateService userHibernateService = UserHibernateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        List<User> list = userJdbcService.getAllUsers();
        List<User> list = userHibernateService.getAllUsers();
        req.setAttribute("users", list);
        getServletContext().getRequestDispatcher("/castom.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
