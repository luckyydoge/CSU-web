package edu.csu.web_last.trigger.http.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.csu.web_last.domain.model.valobj.UserVO;
import edu.csu.web_last.domain.service.login.DefaultLogin;
import edu.csu.web_last.domain.service.login.ILogin;
import edu.csu.web_last.trigger.http.utils.IUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class LoginServlet extends HttpServlet {

    ILogin login = new DefaultLogin();
    Logger log = Logger.getLogger(this.getClass().getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cookieName = "jwt";
        String cookieValue = IUtils.parseCookie(request, cookieName);
        if (!cookieValue.isEmpty() && login.loginCheck(cookieValue)) {

            response.sendRedirect(request.getContextPath() + "/admin/dashboard.html");
            return;

        }
        InputStream inputStream = request.getInputStream();

        // 创建ObjectMapper实例
        ObjectMapper objectMapper = new ObjectMapper();

        UserVO userVO = objectMapper.readValue(inputStream, UserVO.class);
        log.info(userVO.toString());
        String username = userVO.getUsername();
        String password = userVO.getPassword();
        Boolean rememberme = userVO.getRememberme();

        if (!login.login(username, password)) {
            log.info("login failed");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String jwt = login.remember(rememberme, username);
        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setPath("/web_last/admin");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);


//        if (loginService.authenticate(username, password)) {
//            // 登录成功，创建会话
//            HttpSession session = request.getSession();
//            session.setAttribute("username", username);
//            response.sendRedirect("welcome"); // 重定向到欢迎页面
//        } else {
//            // 登录失败，返回登录页面并显示错误信息
//            request.setAttribute("error", "用户名或密码错误");
//            request.getRequestDispatcher("login.jsp").forward(request, response);
//        }
    }
}
