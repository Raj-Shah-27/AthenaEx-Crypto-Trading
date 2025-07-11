package com.rns2706.athenaex.request;

import com.rns2706.athenaex.domain.VerificationType;
import lombok.Data;

@Data
public class ForgotPasswordTokenRequest {
    private String sendTo;
    private VerificationType verificationType;
}
