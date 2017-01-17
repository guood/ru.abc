package ru.abc.common.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.abc.common.dto.Response;
import ru.abc.common.exceptions.ApplicationException;
import ru.abc.common.messages.Messages;

public class BaseController {

    @Autowired
    protected Messages messages;

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response validationExceptionHandler(Exception ex) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        if (ex instanceof MethodArgumentNotValidException) {
            String message = messages.getMessage(((MethodArgumentNotValidException)ex).getBindingResult().getFieldError().getDefaultMessage());
            logger.error(message);
            return new Response(Response.VALIDATION_ERROR_CODE, message);
        } else if (ex instanceof ApplicationException) {
            ApplicationException aex = (ApplicationException)ex;
            String message = messages.getMessage(aex.getMessageCode(), aex.getParams());
            logger.error(message, ex.getCause());
            return new Response(aex.getErrorCode(), message);
        }
        logger.error("Error: ", ex);
        return new Response(Response.SYSTEM_ERROR_CODE, ex.getMessage() == null ? "System error" : ex.getMessage());
    }
}
