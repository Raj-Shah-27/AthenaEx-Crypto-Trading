package com.rns2706.athenaex.service;

import com.rns2706.athenaex.model.PaymentDetails;
import com.rns2706.athenaex.model.User;

public interface PaymentDetailsService {
    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName,
                                            String ifsc, String bankName, User user);

    public PaymentDetails getUsersPaymentDetails(User user);

}
