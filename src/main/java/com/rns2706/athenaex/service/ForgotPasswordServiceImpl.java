package com.rns2706.athenaex.service;

import com.rns2706.athenaex.repo.ForgotPasswordRepo;
import com.rns2706.athenaex.domain.VerificationType;
import com.rns2706.athenaex.model.ForgotPasswordToken;
import com.rns2706.athenaex.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService{

    @Autowired
    private ForgotPasswordRepo forgotPasswordRepo;
    @Override
    public ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo) {
        ForgotPasswordToken token = new ForgotPasswordToken();
        token.setUser(user);
        token.setSendTo(sendTo);
        token.setVerificationType(verificationType);
        token.setId(id);
        return forgotPasswordRepo.save(token);
    }

    @Override
    public ForgotPasswordToken findById(String id) {
        Optional<ForgotPasswordToken> token = forgotPasswordRepo.findById((id));
        return token.orElse(null);
    }

    @Override
    public ForgotPasswordToken findByUser(Long userId) {
        return forgotPasswordRepo.findByUserId(userId);
    }

    @Override
    public void deleteToken(ForgotPasswordToken forgotPasswordToken) {
        forgotPasswordRepo.delete(forgotPasswordToken);
    }
}
