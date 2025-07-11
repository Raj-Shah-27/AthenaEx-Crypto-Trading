package com.rns2706.athenaex.service;

import com.rns2706.athenaex.repo.VerificationCodeRepo;
import com.rns2706.athenaex.domain.VerificationType;
import com.rns2706.athenaex.model.User;
import com.rns2706.athenaex.model.VerificationCode;
import com.rns2706.athenaex.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    private VerificationCodeRepo verificationCodeRepo;

    @Override
    public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {
        VerificationCode verificationCode1 = new VerificationCode();
        verificationCode1.setOtp(OtpUtils.generateOTP());
        verificationCode1.setVerificationType(verificationType);
        verificationCode1.setUser(user);
        return verificationCodeRepo.save(verificationCode1);
    }

    @Override
    public VerificationCode getVerificationCodeById(Long id) throws Exception {
        Optional<VerificationCode> verificationCode = verificationCodeRepo.findById(id);
        if(verificationCode.isPresent()){
            return verificationCode.get();
        }
        throw new Exception("Verifcation code not found");
    }

    @Override
    public VerificationCode getVerificationCodebyUser(Long userId) {
        return verificationCodeRepo.findByUserId(userId);
    }

    @Override
    public void deleteVerificationCodeById(VerificationCode verificationCode) {
        verificationCodeRepo.delete(verificationCode);
    }
}
