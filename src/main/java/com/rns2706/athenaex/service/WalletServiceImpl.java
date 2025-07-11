package com.rns2706.athenaex.service;

import com.rns2706.athenaex.domain.OrderType;
import com.rns2706.athenaex.model.Order;
import com.rns2706.athenaex.model.User;
import com.rns2706.athenaex.model.Wallet;
import com.rns2706.athenaex.model.WalletTransaction;
import com.rns2706.athenaex.repo.WalletRepo;
import com.rns2706.athenaex.repo.WalletTransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService{

    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    private WalletTransactionRepo walletTransactionRepo;



    public Wallet genrateWallet(User user) {
        Wallet wallet=new Wallet();
        wallet.setUser(user);
        return walletRepo.save(wallet);
    }

    @Override
    public Wallet getUserWallet(User user) throws Exception {

        Wallet wallet = walletRepo.findByUserId(user.getId());
        if (wallet != null) {
            return wallet;
        }

        wallet = genrateWallet(user);
        return wallet;
    }


    @Override
    public Wallet findWalletById(Long id) throws Exception {
        Optional<Wallet> wallet=walletRepo.findById(id);
        if(wallet.isPresent()){
            return wallet.get();
        }
        throw new Exception("Wallet not found");
    }

    @Override
    public Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception {
        Wallet senderWallet = getUserWallet(sender);


        if (senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) {
            throw new Exception("Insufficient balance...");
        }

        BigDecimal senderBalance = senderWallet.getBalance().subtract(BigDecimal.valueOf(amount));
        senderWallet.setBalance(senderBalance);
        walletRepo.save(senderWallet);


        BigDecimal receiverBalance = receiverWallet.getBalance().add(BigDecimal.valueOf(amount));
        receiverWallet.setBalance(receiverBalance);
        walletRepo.save(receiverWallet);

        return senderWallet;
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) throws Exception {
        Wallet wallet = getUserWallet(user);

        WalletTransaction walletTransaction=new WalletTransaction();
        walletTransaction.setWallet(wallet);
        walletTransaction.setPurpose(order.getOrderType()+ " " + order.getOrderItem().getCoin().getId() );

        walletTransaction.setDate(LocalDate.now());
        walletTransaction.setTransferId(order.getOrderItem().getCoin().getSymbol());


        if(order.getOrderType().equals(OrderType.BUY)){
            walletTransaction.setAmount(-order.getPrice().longValue());
            BigDecimal newBalance = wallet.getBalance().subtract(order.getPrice());

            if (newBalance.compareTo(order.getPrice())<0) {
                throw new Exception("Insufficient funds for this transaction.");
            }
            wallet.setBalance(newBalance);
        }
        else if(order.getOrderType().equals(OrderType.SELL)){
            walletTransaction.setAmount(order.getPrice().longValue());
            BigDecimal newBalance = wallet.getBalance().add(order.getPrice());
            wallet.setBalance(newBalance);
        }

        walletTransactionRepo.save(walletTransaction);
        walletRepo.save(wallet);
        return wallet;
    }

    @Override
    public Wallet addBalanceToWallet(Wallet wallet, Long money) throws Exception {

        wallet.setBalance(wallet.getBalance().add(BigDecimal.valueOf(money)));
        return walletRepo.save(wallet);
    }


}
