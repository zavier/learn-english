package le.zavier.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import le.zavier.commons.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private boolean isStaticFile(String servletPath) {
        if (servletPath.endsWith(".js") || servletPath.endsWith(".css") || servletPath.endsWith(".eot")
            || servletPath.endsWith(".svg") || servletPath.endsWith("ttf") || servletPath.endsWith("woff")
            || servletPath.endsWith(".woff2")) {
            return true;
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        Object user = session.getAttribute(Const.CURRENT_USER);

        String requestURI = httpServletRequest.getRequestURI();
        logger.info("FILTER URL:{}", requestURI);
        if (isStaticFile(httpServletRequest.getServletPath()) || requestURI.startsWith("/user/login") || user != null) {
            chain.doFilter(request, response);
        } else {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/user/login");
        }
    }

    @Override
    public void destroy() {

    }
}
