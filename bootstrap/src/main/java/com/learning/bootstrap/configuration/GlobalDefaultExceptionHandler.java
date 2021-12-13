package com.learning.bootstrap.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @ClassName GlobalDefaultExceptionHandler
 * @Description TODO
 * @Author hufei
 * @Date 2020/7/24 10:14
 * @Version 1.0
 */
@ControllerAdvice
@ResponseBody
public class GlobalDefaultExceptionHandler {

    private Logger log = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);
    private ObjectWriter converter = new ObjectMapper().writer();

    public GlobalDefaultExceptionHandler() {

    }

    /**
     * @Description 捕获所有抛出的异常
     * @Param [req, resp, e]
     * @Return int
     * @Author jiashudong
     * @Date 2020/5/6 15:32
     */
    @ExceptionHandler(value = Throwable.class)
    public void defaultErrorHandler(HttpServletRequest req, HttpServletResponse resp, Exception e) throws Exception {
        log.error(String.format("Request: %s raised and error {}", req.getRequestURL()), e);
        serializeError(e, resp, HttpStatus.INTERNAL_SERVER_ERROR, req);
    }

    /**
     * @Description 捕获请求方法名的异常
     * @Param [req, resp, e]
     * @Return int
     * @Author jiashudong
     * @Date 2020/5/6 15:32
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public void requestMethodNotSupportedException(HttpServletRequest req, HttpServletResponse resp,
                                                   Exception e) throws Exception {
        serializeError(e, resp, HttpStatus.NOT_ACCEPTABLE, req);
    }

    /**
     * @Description 处理捕获的异常
     * @Param [req, resp, e]
     * @Return int
     * @Author jiashudong
     * @Date 2020/5/6 15:32
     */
    private void serializeError(Exception ex, HttpServletResponse response, HttpStatus status,
                                final HttpServletRequest req) throws IOException {
        log.error("Request: " + req.getRequestURL() + " raised and error {}", ex.toString());
        log.error("Errors processing request", ex);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        converter.writeValue(response.getOutputStream(), getStackTrace(ex));
    }

    private String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}