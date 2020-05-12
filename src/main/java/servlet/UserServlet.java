package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/user")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            String name = session.getAttribute("role").toString();
            String lastName = session.getAttribute("lastName").toString();
            session.setAttribute("message", "Приветствую вас " + name + " " + lastName);
        } catch (Exception e) {
            session.setAttribute("message", "<h1>Вы зашли как аноним. Информация не доступна.</h1>");
            System.out.println("No information for you");
        }
        getServletContext().getRequestDispatcher("/WEB-INF/user.jsp").forward(req, resp);
    }
}
