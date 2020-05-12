package servlet;

import model.User;
import service.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/editUser")
public class EditUserServlet extends HttpServlet {
    Service service = Service.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("name").isEmpty() || req.getParameter("lastName").isEmpty() || req.getParameter("password").isEmpty()) {
            System.out.println("форма не заполнена");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            long id = Long.parseLong(req.getParameter("id"));
            String newName = req.getParameter("name");
            String newLastName = req.getParameter("lastName");
            String newPass = req.getParameter("password");
            String role = req.getParameter("combobox");
            User user = new User(id, newName, newLastName, newPass, role);
            service.editUser(user);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
        resp.sendRedirect("/admin");
    }
}
