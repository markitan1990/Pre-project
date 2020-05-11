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
    ServletContext ctx = filterConfig.getServletContext();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response =(HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        String reqe = request.getMethod();
        if (request.getMethod().equals("GET")) {
            boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
            if (isLoggedIn) {
                ctx.getRequestDispatcher("/" + session.getAttribute("user").toString().toLowerCase()).forward(request, response);
                return;
            }
            ctx.getRequestDispatcher("/login.jsp").forward(request, response);
        } else if (reqe.equals("POST")) {
            User user = null;
            try {
                user = service.getUser(new User(request.getParameter("name"),
                        request.getParameter("lastName"), request.getParameter("password")));
                if (user.getRole().equals("Admin")) {
                    session.setAttribute("user", user);
                } else if (user.getRole().equals("User")) {
                    session.setAttribute("user", user);
                }
            } catch (Exception e) {
                System.out.println("такого пользователя нет");
            }
            User user1 = (User) session.getAttribute("user");
            String path = user1.getRole().toLowerCase();
            response.sendRedirect("/" + path);
        }
    }

    @Override
    public void destroy() {

    }
}
