package com.rns2706.athenaex.service;

import com.rns2706.athenaex.model.PaymentDetails;
import com.rns2706.athenaex.model.User;
import com.rns2706.athenaex.repo.PaymentDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentDetailsServiceImpl implements PaymentDetailsService{

    @Autowired
    private PaymentDetailsRepo paymentDetailsRepo;

    @Override
    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName,
                                            String ifsc, String bankName, User user) {
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setAccountNumber(accountNumber);
        paymentDetails.setAccountHolderName(accountHolderName);
        paymentDetails.setIfsc(ifsc);
        paymentDetails.setBankName(bankName);
        paymentDetails.setUser(user);
        return paymentDetailsRepo.save(paymentDetails);
    }

    @Override
    public PaymentDetails getUsersPaymentDetails(User user) {
        return paymentDetailsRepo.getPaymentDetailsByUserId(user.getId());
    }
}

