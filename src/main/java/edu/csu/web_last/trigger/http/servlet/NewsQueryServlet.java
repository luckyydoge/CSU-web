package edu.csu.web_last.trigger.http.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.csu.web_last.trigger.http.type.NewsListResponse;
import edu.csu.web_last.trigger.http.type.ResponseData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@WebServlet("/api/queryNews")
public class NewsQueryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newsPath = request.getServletContext().getRealPath("/").concat("news/news");

        Path news = Paths.get(newsPath);
        if (!Files.exists(news)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        List<String> newsList = Files.readAllLines(news);
        ResponseData responseData = new ResponseData(newsList);
        response.setStatus(HttpServletResponse.SC_OK);
        String responseJSON = new ObjectMapper().writeValueAsString(responseData);
        response.setContentType("application/json");
        response.getWriter().write(responseJSON);
    }
}
