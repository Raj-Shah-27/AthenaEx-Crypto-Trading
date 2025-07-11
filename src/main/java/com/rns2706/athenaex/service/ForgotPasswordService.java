package com.rns2706.athenaex.service;

import com.rns2706.athenaex.domain.VerificationType;
import com.rns2706.athenaex.model.ForgotPasswordToken;
import com.rns2706.athenaex.model.User;

public interface ForgotPasswordService {
    ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo);
    ForgotPasswordToken findById(String id);
    ForgotPasswordToken findByUser(Long userId);
    void deleteToken(ForgotPasswordToken forgotPasswordToken);
}
