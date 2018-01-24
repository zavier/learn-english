package le.zavier.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import le.zavier.commons.ResultBean;
import le.zavier.commons.LoginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 登录过滤器
 *
 */
public class LoginFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    /**
     * 不需要登录的url的开头部分
     */
    private List<String> noNeedLoginUrlStarts = new ArrayList<>();

    public void setNoNeedLoginUrlStarts(List<String> noNeedLoginUrlStarts) {
        this.noNeedLoginUrlStarts = noNeedLoginUrlStarts;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        if (isNeedLoginUri(requestURI) && isNotLogin(httpRequest)) {
            if (isAjaxRequest(httpRequest)) {
                returnNoLoginInfo(httpResponse);
            } else {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/user/login");
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private void returnNoLoginInfo(HttpServletResponse httpResponse) throws IOException {
        PrintWriter writer = httpResponse.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(ResultBean.createByNotLogin()));
        writer.flush();
    }

    private boolean isNotLogin(HttpServletRequest httpRequest) {
        return ! LoginManager.isLogin(httpRequest.getSession());
    }

    private boolean isNeedLoginUri(String uri) {
        boolean needLogin = noNeedLoginUrlStarts.stream().noneMatch(uri::startsWith);
        return needLogin ? true : false;
    }

    private boolean isAjaxRequest(HttpServletRequest httpRequest) {
        String header = httpRequest.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equalsIgnoreCase(header)) {
            return true;
        }
        return false;
    }

    @Override
    public void destroy() {}
}
