package com.rns2706.athenaex.service;

import com.rns2706.athenaex.domain.VerificationType;
import com.rns2706.athenaex.model.User;
import com.rns2706.athenaex.model.VerificationCode;

public interface VerificationCodeService {

    VerificationCode sendVerificationCode(User user, VerificationType verificationType);
    VerificationCode getVerificationCodeById(Long id) throws Exception;
    VerificationCode getVerificationCodebyUser(Long userId);

    void deleteVerificationCodeById(VerificationCode verificationCode);
}
