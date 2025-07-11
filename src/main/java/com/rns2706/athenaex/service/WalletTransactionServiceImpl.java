package com.rns2706.athenaex.service;

import com.rns2706.athenaex.domain.WalletTransactionType;
import com.rns2706.athenaex.model.*;
import com.rns2706.athenaex.repo.WalletTransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WalletTransactionServiceImpl implements WalletTransactionService{

    @Autowired
    private WalletTransactionRepo walletTransactionRepo;


    @Override
    public WalletTransaction createTransaction(Wallet wallet,
                                               WalletTransactionType type,
                                               String transferId,
                                               String purpose,
                                               Long amount
    ) {
        WalletTransaction transaction = new WalletTransaction();
        transaction.setWallet(wallet);
        transaction.setDate(LocalDate.now());
        transaction.setType(type);
        transaction.setTransferId(transferId);
        transaction.setPurpose(purpose);
        transaction.setAmount(amount);

        return walletTransactionRepo.save(transaction);
    }

    @Override
    public List<WalletTransaction> getTransactions(Wallet wallet, WalletTransactionType type) {
        return walletTransactionRepo.findByWalletOrderByDateDesc(wallet);
    }
}
