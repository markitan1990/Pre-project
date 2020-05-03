package servlet;


import model.User;
import service.UserService;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(value = "/users")
public class UserServlet extends HttpServlet {
    UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> list = userService.getAllUsers();
        req.setAttribute("users", list);
        getServletContext().getRequestDispatcher("/castom.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        processRequest(req, resp);
        doGet(req,resp);
    }

    public int checkParametr(HttpServletRequest req) {
        if (req.getParameter("Add") != null) {
            return 1;
        }
        if (req.getParameter("Delete") != null) {
            return 2;
        }
        if (req.getParameter("Edit") != null) {
            return 3;
        }
        return 0;
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (checkParametr(req) == 1) {
            User user = new User(req.getParameter("name"), req.getParameter("lastName"), Long.parseLong(req.getParameter("age")));
            try {
                userService.addUser(user);
            } catch (SQLException e) {
                System.out.println("не получилось добавить пользователя");
            }
            getServletContext().getRequestDispatcher("/castom.jsp").forward(req, resp);
        }
        if (checkParametr(req) == 2) {
            long id = Long.parseLong(req.getParameter("Delete"));

            if (userService.deleteUser(id)) {
                System.out.println("Удаление прошло успешно");
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            getServletContext().getRequestDispatcher("/castom.jsp").forward(req, resp);
        }
        if (checkParametr(req) == 3) {
            long id = Long.parseLong(req.getParameter("id"));
            String newName = req.getParameter("name");
            String newLastName = req.getParameter("lastName");
            long newAge = Long.parseLong(req.getParameter("age"));
            User user = new User(id, newName, newLastName, newAge);
            userService.editUser(user);
            getServletContext().getRequestDispatcher("/castom.jsp").forward(req, resp);
        }

    }
}
