package com.rns2706.athenaex.repo;

import com.rns2706.athenaex.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepo extends JpaRepository<Wallet, Long> {

    Wallet findByUserId(Long userId);
}
