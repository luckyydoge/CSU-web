package edu.csu.web_last.trigger.http.filter;

import edu.csu.web_last.domain.service.login.DefaultLogin;
import edu.csu.web_last.domain.service.login.ILogin;
import edu.csu.web_last.trigger.http.utils.IUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LoginFilter implements Filter {
    ILogin login = new DefaultLogin();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String cookieName = "jwt";
        String cookieValue = IUtils.parseCookie(httpRequest, cookieName);
        if (!cookieValue.isEmpty() && login.loginCheck(cookieValue)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/dashboard.html");
            return;
        }
        filterChain.doFilter(httpRequest, httpResponse);
    }
}
