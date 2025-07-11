package com.rns2706.athenaex.service;

import com.rns2706.athenaex.model.TwoFactOTP;
import com.rns2706.athenaex.model.User;

public interface TwoFactOTPService {
    TwoFactOTP createTwoFactorOTP(User user, String otp, String jwt);
    TwoFactOTP findByUser(Long userId);
    TwoFactOTP findById(String id);
    boolean verifyTwoFactOTP(TwoFactOTP twoFactOTP, String otp);
    void deleteTwoFactOTP(TwoFactOTP twoFactOTP);
}
