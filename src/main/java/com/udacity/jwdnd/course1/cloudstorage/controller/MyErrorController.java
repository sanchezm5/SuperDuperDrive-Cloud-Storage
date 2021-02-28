package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")

public class MyErrorController implements ErrorController {

    @GetMapping()
    public String handleError(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                redirectAttributes.addFlashAttribute("notFoundStatus", "The page you are looking for is not found.");
                //return "redirect:/login";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                redirectAttributes.addFlashAttribute("serverErrorStatus", "There has been an internal server error. Please try again later!");
                //return "redirect:/login";
            }

        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
