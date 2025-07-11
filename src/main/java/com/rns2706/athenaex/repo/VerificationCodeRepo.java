package com.rns2706.athenaex.repo;

import com.rns2706.athenaex.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepo extends JpaRepository<VerificationCode, Long> {

    public VerificationCode findByUserId(Long userId);
}
