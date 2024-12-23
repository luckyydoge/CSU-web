package edu.csu.web_last.trigger.http.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@WebServlet("/proxy")
public class ProxyServlet extends HttpServlet {
    private HttpClient httpClient = HttpClient.newHttpClient();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl = request.getParameter("url");
        if (null == targetUrl || targetUrl.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        try {
            HttpRequest proxyRequest = HttpRequest.newBuilder()
                    .uri(URI.create(targetUrl))
                    .GET()
                    .build();

            HttpResponse<String> proxyResponse = httpClient.send(proxyRequest, HttpResponse.BodyHandlers.ofString());
            response.setStatus(proxyResponse.statusCode());
            proxyResponse.headers()
                    .map()
                    .forEach((name, values) -> {
                        for (String value : values) {
                            response.addHeader(name, value);
                        }
                    });
            response.getWriter().write(proxyResponse.body());
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }


    }
}
