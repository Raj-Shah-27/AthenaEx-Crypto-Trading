package com.rns2706.athenaex.controller;

import com.rns2706.athenaex.service.EmailService;
import com.rns2706.athenaex.service.UserService;
import com.rns2706.athenaex.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationController {
    private final VerificationCodeService verificationCodeService;
    private final UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    public VerificationController(VerificationCodeService verificationService, UserService userService) {
        this.verificationCodeService = verificationService;
        this.userService = userService;
    }
}