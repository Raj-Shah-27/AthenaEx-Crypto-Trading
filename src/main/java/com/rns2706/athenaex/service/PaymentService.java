package com.rns2706.athenaex.service;

import com.rns2706.athenaex.domain.PaymentMethod;
import com.rns2706.athenaex.model.*;
import com.rns2706.athenaex.response.PaymentResponse;

public interface PaymentService {
    PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    Boolean ProccedPaymentOrder (PaymentOrder paymentOrder, String paymentId) throws Exception;

    PaymentResponse createRazorpayPaymentLink(User user, Long Amount, Long orderId) throws Exception;

    //PaymentResponse createStripePaymentLink(User user, Long Amount, Long orderId) throws Exception;
}
