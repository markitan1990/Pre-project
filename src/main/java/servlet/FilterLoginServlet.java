package servlet;

import model.User;
import service.Service;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/login")
public class FilterLoginServlet implements Filter {
    private FilterConfig filterConfig;
    private Service service = Service.getInstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        ServletContext ctx = filterConfig.getServletContext();

        String reqe = request.getMethod();
        if (request.getMethod().equals("GET")) {
            boolean isLoggedIn = (session != null && session.getAttribute("role") != null);
            if (isLoggedIn) {
                ctx.getRequestDispatcher("/" + session.getAttribute("role").toString().toLowerCase()).forward(request, response);
            }
            ctx.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } else if (reqe.equals("POST")) {
            User user = null;
            try {
                user = service.getUser(new User(request.getParameter("name"),
                        request.getParameter("lastName"), request.getParameter("password")));
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
            response.sendRedirect("/" + path);
        }
    }
}
