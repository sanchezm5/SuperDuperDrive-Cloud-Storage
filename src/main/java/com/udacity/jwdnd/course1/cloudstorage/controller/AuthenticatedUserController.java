package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationUserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/userset")
public class AuthenticatedUserController {

    private AuthenticationUserService authenticatedUserService;

    public AuthenticatedUserController(AuthenticationUserService authenticatedUserService) {
        this.authenticatedUserService = authenticatedUserService;
    }

    @GetMapping()
    public String getHomePage(Authentication authentication, RedirectAttributes redirectAttributes) {
        authenticatedUserService.setUser(authentication.getName());
        return "redirect:/home";
    }
}