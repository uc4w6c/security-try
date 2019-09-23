package com.example.demo.controller;

import com.example.demo.exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class ExceptionController {
    @ExceptionHandler(NotFoundException.class)
    public String NotFoundExceptionHandler() {
        return "err/notfound";
    }
}
