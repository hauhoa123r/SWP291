package org.project.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;


@ControllerAdvice
public class ActivityLoggingExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger("ERROR_LOG");

    @ExceptionHandler(Exception.class)
    public void handleException(Exception ex, HttpServletRequest request) {
        String user = request.getRemoteUser() != null ? request.getRemoteUser() : "anonymous";
        String uri = request.getRequestURI();
        String method = request.getMethod();

        logger.error("❌ [{}] {} {} | User: {} | Message: {}",
                LocalDateTime.now(), method, uri, user, ex.getMessage());
    }
}
