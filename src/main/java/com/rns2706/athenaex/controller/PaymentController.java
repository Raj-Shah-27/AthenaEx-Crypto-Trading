package com.rns2706.athenaex.controller;

import com.rns2706.athenaex.domain.PaymentMethod;
import com.rns2706.athenaex.model.PaymentOrder;
import com.rns2706.athenaex.model.User;
import com.rns2706.athenaex.response.PaymentResponse;
import com.rns2706.athenaex.service.PaymentService;
import com.rns2706.athenaex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;



    @PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(
            @PathVariable PaymentMethod paymentMethod,
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        PaymentResponse paymentResponse = new PaymentResponse();

        PaymentOrder order= paymentService.createOrder(user, amount,paymentMethod);

        if(paymentMethod.equals(PaymentMethod.RAZORPAY)){
            paymentResponse=paymentService.createRazorpayPaymentLink(user,amount,
                    order.getId());
        }
//        else{
//            paymentResponse=paymentService.createStripePaymentLink(user,amount, order.getId());
//        }

        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }


}
