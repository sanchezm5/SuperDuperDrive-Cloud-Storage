package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping("/add")
    public String addCredential(@ModelAttribute("credentialForm")CredentialForm credentialForm, Authentication authentication, RedirectAttributes redirectAttributes) {
        if(credentialForm.getCredentialId() == null) {
            credentialService.addCredential(credentialForm);
        } else {
            credentialService.updateCredential(credentialForm);
            redirectAttributes.addFlashAttribute("editCredentialSuccess", true);
            return "redirect:/result";
        }
        return "redirect:/result";
    }
}
