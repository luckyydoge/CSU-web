package edu.csu.web_last.trigger.http.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.csu.web_last.trigger.http.type.ImgListResponse;
import edu.csu.web_last.trigger.http.type.ResponseData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet("/api/queryImg")
public class FileQueryServlet extends HttpServlet {

    Logger log = Logger.getLogger(this.getClass().getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imgDirPath = request.getServletContext().getRealPath("/").concat("img\\");
        String hostname = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/img/";
        log.info(hostname);

        File imgFolder = new File(imgDirPath);
        ImgListResponse imgListResponse = new ImgListResponse();

        try (Stream<Path> paths = Files.walk(imgFolder.toPath())) {
            List<String> imgNames = paths.filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .map(filename -> hostname + filename)
                    .toList();
            imgListResponse.setImages(imgNames);
            imgListResponse.setCount(imgNames.size());
        }
        response.setContentType("image/json");
        response.setCharacterEncoding("UTF-8");
        ResponseData responseData = new ResponseData(imgListResponse);
        String jsonString = new ObjectMapper().writeValueAsString(responseData);
        log.info(jsonString);
        response.getWriter().write(jsonString);

    }
}
