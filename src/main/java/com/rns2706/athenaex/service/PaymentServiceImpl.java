package com.rns2706.athenaex.service;


import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.rns2706.athenaex.domain.PaymentMethod;
import com.rns2706.athenaex.domain.PaymentOrderStatus;
import com.rns2706.athenaex.model.*;
import com.rns2706.athenaex.repo.PaymentOrderRepo;
import com.rns2706.athenaex.response.PaymentResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService{


    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecret;

    @Autowired
    private PaymentOrderRepo paymentOrderRepo;


    @Override
    public PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod) {
        PaymentOrder order=new PaymentOrder();
        order.setUser(user);
        order.setAmount(amount);
        order.setPaymentMethod(paymentMethod);
        return paymentOrderRepo.save(order);
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        Optional<PaymentOrder> optionalPaymentOrder=paymentOrderRepo.findById(id);
        if(optionalPaymentOrder.isEmpty()){
            throw new Exception("Payment order not found");
        }
        return optionalPaymentOrder.get();
    }

    @Override
    public Boolean ProccedPaymentOrder(PaymentOrder paymentOrder,String paymentId) throws Exception {
        if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)){

            if(paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY)){
                RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);
                Payment payment = razorpay.payments.fetch(paymentId);

                Integer amount = payment.get("amount");
                String status = payment.get("status");
                if(status.equals("captured")){
                    paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);

                    return true;
                }
                paymentOrder.setStatus(PaymentOrderStatus.FAILED);
                paymentOrderRepo.save(paymentOrder);
                return false;
            }
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
            paymentOrderRepo.save(paymentOrder);
            paymentOrderRepo.save(paymentOrder);
            return true;
        }

        return false;
    }

    @Override
    public PaymentResponse createRazorpayPaymentLink(User user, Long Amount, Long orderId) throws Exception {

        Long amount = Amount * 100;


        try {
            RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);

            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount",amount);
            paymentLinkRequest.put("currency","INR");

            JSONObject customer = new JSONObject();
            customer.put("name",user.getName());

            customer.put("email",user.getEmail());
            paymentLinkRequest.put("customer",customer);

            JSONObject notify = new JSONObject();
            notify.put("email",true);
            paymentLinkRequest.put("notify",notify);

            paymentLinkRequest.put("reminder_enable",true);

            paymentLinkRequest.put("callback_url","http://localhost:5173/wallet/"+orderId);
            paymentLinkRequest.put("callback_method","get");

            PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

            String paymentLinkId = payment.get("id");
            String paymentLinkUrl = payment.get("short_url");

            PaymentResponse res=new PaymentResponse();
            res.setPayment_url(paymentLinkUrl);


            return res;

        } catch (Exception e) {

            System.out.println("Error creating payment link: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

//    @Override
//    public PaymentResponse createStripePaymentLink(User user, Long amount,Long orderId) throws StripeException {
//        Stripe.apiKey = stripeSecretKey;
//
//        SessionCreateParams params = SessionCreateParams.builder()
//                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
//                .setMode(SessionCreateParams.Mode.PAYMENT)
//                .setSuccessUrl("http://localhost:5173/wallet?order_id="+orderId)
//                .setCancelUrl("http://localhost:5173/payment/cancel")
//                .addLineItem(SessionCreateParams.LineItem.builder()
//                        .setQuantity(1L)
//                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
//                                .setCurrency("usd")
//                                .setUnitAmount(amount*100)
//                                .setProductData(SessionCreateParams
//                                        .LineItem
//                                        .PriceData
//                                        .ProductData
//                                        .builder()
//                                        .setName("Top up wallet")
//                                        .build()
//                                ).build()
//                        ).build()
//                ).build();
//
//        Session session = Session.create(params);
//
//        PaymentResponse res = new PaymentResponse();
//        res.setPayment_url(session.getUrl());
//
//        return res;
//    }
}
