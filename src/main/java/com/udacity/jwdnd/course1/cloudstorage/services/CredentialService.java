package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;
    private AuthenticationUserService authenticationUserService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService, AuthenticationUserService authenticationUserService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
        this.authenticationUserService = authenticationUserService;
    }

    public List<Credential> getCredentials(Integer userId) {
        List<Credential> credentialList;
        try {
            credentialList = credentialMapper.getCredentials(authenticationUserService.getLoggedInUserId());
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw e;
        }
        return credentialList;
    }

    public Credential getCredential(Integer credentialId) {
        Credential credential;
        try {
            credential = credentialMapper.getCredential(credentialId);
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw e;
        }
        return credential;
    }

    public int addCredential(CredentialForm credentialForm) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);
        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);

        return credentialMapper.insertCredential(new Credential(0,
                credentialForm.getUrl(),
                credentialForm.getUsername(),
                encodedKey,encryptedPassword,
                authenticationUserService.getLoggedInUserId()));
    }

    public void updateCredential(CredentialForm credentialForm) {
        Credential credential = credentialMapper.getCredential(credentialForm.getCredentialId());
        credential.setUrl(credentialForm.getUrl());
        credential.setUsername(credentialForm.getUsername());
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), credential.getKey());
        credential.setPassword(encryptedPassword);
        credentialMapper.updateCredential(credential);
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }
}

