package servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/admin")
public class FilterServlet implements Filter {
    private FilterConfig filterConfig;
    HttpServletRequest request = null;
    HttpServletResponse response = null;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        request = (HttpServletRequest) servletRequest;
        response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        ServletContext ctx = filterConfig.getServletContext();
        String referrer = request.getHeader("referer");

        boolean isLoggedIn = (session != null && session.getAttribute("role") != null);
        if (isLoggedIn) {
            String role = session.getAttribute("role").toString().toLowerCase();
            if (role.equals("user")) {
                response.sendRedirect("/user");
            } else {
                ctx.getRequestDispatcher(request.getRequestURI()).forward(request, response);
            }
        } else {
            response.sendRedirect("/login");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }
}
