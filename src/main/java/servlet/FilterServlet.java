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

        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
        if (isLoggedIn) {
            String role = session.getAttribute("user").toString().toLowerCase();
            if (role.equals("user")) {
                response.sendRedirect("/user");
            } else {
                ctx.getRequestDispatcher(request.getRequestURI()).forward(request, response);
            }
        } else {
            response.sendRedirect("/login");
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
