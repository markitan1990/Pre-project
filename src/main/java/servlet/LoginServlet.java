package servlet;

import model.User;
import service.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {
    private Service service = Service.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String loginURI = req.getRequestURI();
        User user = null;
        try {
             user = service.getUser(new User(req.getParameter("name"), req.getParameter("lastName"), req.getParameter("password")));
            if (user.getRole().equals("Admin")) {
                session.setAttribute("role", "Admin");
                session.setAttribute("name", user.getName());
                session.setAttribute("lastName", user.getLastName());
                session.setAttribute("password", user.getPassword());
            } else if (user.getRole().equals("User")) {
                session.setAttribute("role", "User");
                session.setAttribute("name", user.getName());
                session.setAttribute("lastName", user.getLastName());
                session.setAttribute("password", user.getPassword());
            }
        } catch (Exception e) {
            System.out.println("такого пользователя нет");
        }
        String path = session.getAttribute("role").toString().toLowerCase();
        resp.sendRedirect("/" + path);
    }
}
