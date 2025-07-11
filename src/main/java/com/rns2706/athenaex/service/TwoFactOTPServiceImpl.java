package com.rns2706.athenaex.service;

import com.rns2706.athenaex.repo.TwoFactOTPRepo;
import com.rns2706.athenaex.model.TwoFactOTP;
import com.rns2706.athenaex.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TwoFactOTPServiceImpl implements TwoFactOTPService{

    @Autowired
    private TwoFactOTPRepo twoFactOTPRepo;

    @Override
    public TwoFactOTP createTwoFactorOTP(User user, String otp, String jwt) {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        TwoFactOTP twoFactOTP = new TwoFactOTP();
        twoFactOTP.setOtp(otp);
        twoFactOTP.setJwt(jwt);
        twoFactOTP.setId(id);
        twoFactOTP.setUser(user);

        return twoFactOTPRepo.save(twoFactOTP);
    }

    @Override
    public TwoFactOTP findByUser(Long userId) {
        return twoFactOTPRepo.findByUserId(userId);
    }

    @Override
    public TwoFactOTP findById(String id) {
        Optional<TwoFactOTP> opt = twoFactOTPRepo.findById(id);
        return opt.orElse(null);
    }

    @Override
    public boolean verifyTwoFactOTP(TwoFactOTP twoFactOTP, String otp) {
        return twoFactOTP.getOtp().equals(otp);
    }

    @Override
    public void deleteTwoFactOTP(TwoFactOTP twoFactOTP) {
        twoFactOTPRepo.delete(twoFactOTP);
    }
}
