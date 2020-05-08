package servlet;

import model.User;
import service.Service;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class FilterServlet implements Filter {
    private FilterConfig filterConfig;
    private Service service = Service.getInstance();
    HttpServletRequest request = null;
    HttpServletResponse response = null;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        request = (HttpServletRequest) servletRequest;
        response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        ServletContext ctx = filterConfig.getServletContext();

        boolean isLoggedIn = (session != null && session.getAttribute("role") != null);

        String loginURI = request.getContextPath() + "/login";
        boolean isLoginRequest = request.getRequestURI().equals(loginURI);
        boolean isLoginPage = request.getRequestURI().endsWith("login");

        User user = null;
        String query = request.getRequestURI();
        if (isLoggedIn && (isLoginPage || isLoginRequest)) {
            RequestDispatcher dispatcher = ctx.getRequestDispatcher("/" + session.getAttribute("role").toString().toLowerCase());
            dispatcher.forward(request, response);
        }
        String re = request.getRequestURI();
        if (isLoggedIn) {
            String us = session.getAttribute("role").toString().toLowerCase();
            String name = session.getAttribute("name").toString();
            String lastName = session.getAttribute("lastName").toString();
            session.setAttribute("message", "Приветствую вас " + name + " " + lastName);
            if (us.equals("user")) {
                ctx.getRequestDispatcher("/user").forward(request, response);
            } else {
                ctx.getRequestDispatcher(re).forward(request, response);
                return;
            }
        } else {
            RequestDispatcher dispatcher = ctx.getRequestDispatcher("/login");
            dispatcher.forward(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {

    }
}
