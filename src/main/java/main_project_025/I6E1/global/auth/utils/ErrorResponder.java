package main_project_025.I6E1.global.auth.utils;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import main_project_025.I6E1.global.exception.BusinessException;
import main_project_025.I6E1.global.exception.ExceptionCode;
import main_project_025.I6E1.global.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

public class ErrorResponder {
    public static void sendErrorResponse(HttpServletResponse response, HttpStatus status) throws IOException {
        Gson gson = new Gson();
        ErrorResponse errorResponse = ErrorResponse.of(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        response.getWriter().write(gson.toJson(errorResponse, ErrorResponse.class));
    }

    public static void sendErrorResponse(HttpServletResponse response, ExceptionCode code) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        throw new BusinessException(code);
    }

}