package edu.csu.web_last.trigger.http.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/admin/updateNews")
public class NewsUpdateServlet extends HttpServlet {
    Logger logger = Logger.getLogger(NewsUpdateServlet.class.getName());
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder jsonBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBody.append(line);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        logger.info(jsonBody.toString());
        List<String> urls = mapper.readValue(jsonBody.toString(), List.class);

        String filePath = request.getServletContext().getRealPath("/").concat("news\\news");

        Files.write(Paths.get(filePath), urls, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        logger.info("urls: " + urls.toString());


    }
}
