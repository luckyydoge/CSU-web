package edu.csu.web_last.trigger.http.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class FileUpdateServlet extends HttpServlet {

    Logger logger = Logger.getLogger("FileUpdateServlet");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        String appPath = context.getRealPath("/");
        appPath = appPath.concat("img\\");
        System.out.println("Application Path: " + appPath);

        Path file = Paths.get(appPath);
//        if (Files.exists(file)) {
//            response.setStatus(HttpServletResponse.SC_CONFLICT);
//            return;
//        }

        Part filePart = request.getPart("file"); // <input type="file" name="imageFile">
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = getFileName(filePart);
            filePart.write(appPath + fileName);

            // 文件上传成功后的响应信息
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        String appPath = context.getRealPath("/");
        appPath = appPath.concat("img\\");
        System.out.println("Application Path: " + appPath);
        String filePath = request.getParameter("filename");

        filePath = appPath.concat(filePath);

        Path file = Paths.get(filePath);
        logger.info("Try to delete file: " + filePath);
        try {
            Files.delete(file);
            logger.info("File deleted: " + file.toString());
        } catch (NoSuchFileException fe) {
            logger.info("File not found: " + file.toString());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        } catch (Exception e) {
            logger.severe("Error deleting file: " + file.toString());
        }




    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
