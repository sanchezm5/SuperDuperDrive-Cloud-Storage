package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @PostMapping("/add")
    public String addCredential(@ModelAttribute("credentialForm")CredentialForm credentialForm, Authentication authentication, RedirectAttributes redirectAttributes) {
        if(credentialForm.getCredentialId() == null) {
            credentialService.addCredential(credentialForm);
        } else {
            credentialService.updateCredential(credentialForm);
            redirectAttributes.addFlashAttribute("editCredentialSuccess", "Credential edited.");
        }
        return "redirect:/result";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId, Authentication authentication, RedirectAttributes redirectAttributes) {
        credentialService.deleteCredential(credentialId);
        redirectAttributes.addFlashAttribute("deleteCredentialSuccess", "Credential deleted.");
        return "redirect:/result";
    }
}
