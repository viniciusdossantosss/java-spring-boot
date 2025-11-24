package com.stockcontrol.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        log.error("Erro não tratado: ", ex);
        
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("error", new ErrorDetails(ex));
        modelAndView.addObject("status", 500);
        
        return modelAndView;
    }
    
    public static class ErrorDetails {
        private final String message;
        private final String type;
        private final String exception;
        
        public ErrorDetails(Exception ex) {
            this.message = ex.getMessage();
            this.type = ex.getClass().getSimpleName();
            this.exception = ex.toString();
        }
        
        public String getMessage() { return message; }
        public String getType() { return type; }
        public String getException() { return exception; }
    }
}
