package servlet;


import service.UserHibernateService;
import service.UserJdbcService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/deleteUser")
public class DeleteUserServlet extends HttpServlet {
    UserJdbcService userJdbcService = UserJdbcService.getInstance();
    UserHibernateService userHibernateService = UserHibernateService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDelete(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("Delete"));
//        if (userJdbcService.deleteUser(id)) {
        if (userHibernateService.deleteUser(id)) {
            System.out.println("Удаление прошло успешно");
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        getServletContext().getRequestDispatcher("/users").forward(req, resp);
    }
}
