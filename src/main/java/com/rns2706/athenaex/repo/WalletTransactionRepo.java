package com.rns2706.athenaex.repo;

import com.rns2706.athenaex.model.Wallet;
import com.rns2706.athenaex.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletTransactionRepo extends JpaRepository<WalletTransaction, Long> {
    List<WalletTransaction> findByWalletOrderByDateDesc(Wallet wallet);
}
