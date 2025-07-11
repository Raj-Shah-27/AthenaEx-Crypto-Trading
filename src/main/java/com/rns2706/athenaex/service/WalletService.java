package com.rns2706.athenaex.service;

import com.rns2706.athenaex.model.Order;
import com.rns2706.athenaex.model.User;
import com.rns2706.athenaex.model.Wallet;

public interface WalletService {
    Wallet getUserWallet(User user) throws Exception;

    Wallet addBalanceToWallet(Wallet wallet, Long money) throws Exception;

    Wallet findWalletById(Long id) throws Exception;

    Wallet walletToWalletTransfer(User sender,Wallet receiverWallet, Long amount) throws Exception;

    Wallet payOrderPayment(Order order, User user) throws Exception;

}
