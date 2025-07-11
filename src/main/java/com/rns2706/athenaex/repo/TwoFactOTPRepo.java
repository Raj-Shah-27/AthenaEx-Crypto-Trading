package com.rns2706.athenaex.repo;

import com.rns2706.athenaex.model.TwoFactOTP;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TwoFactOTPRepo extends JpaRepository<TwoFactOTP, String> {
    TwoFactOTP findByUserId(Long userId);
}
