package com.onix.hcmustour.exception;

import com.onix.hcmustour.dto.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationException.EntityNotFoundException.class)
    public final ResponseEntity handleNotFountExceptions(Exception ex, WebRequest request) {
        Response<Object> response = Response.notFound();
        response.addErrorMsgToResponse(ex.getMessage(), ex);
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApplicationException.DuplicateEntityException.class)
    public final ResponseEntity handleDuplicateExceptions(Exception ex, WebRequest request) {
        Response<Object> response = Response.duplicateEntity();
        response.addErrorMsgToResponse(ex.getMessage(), ex);
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApplicationException.EntityAlreadyUsedException.class)
    public final ResponseEntity handleEntityAlreadyUsedException(Exception ex, WebRequest request) {
        Response<Object> response = Response.useElsewhere();
        response.addErrorMsgToResponse(ex.getMessage(), ex);
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApplicationException.DefaultException.class)
    public final ResponseEntity handleDefaultException(Exception ex, WebRequest request) {
        Response<Object> response = Response.exception();
        response.addErrorMsgToResponse(ex.getMessage(), ex);
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }
}
