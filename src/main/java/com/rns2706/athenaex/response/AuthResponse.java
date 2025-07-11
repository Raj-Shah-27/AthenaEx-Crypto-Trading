package com.rns2706.athenaex.response;

import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private  boolean status;
    private String message;
    private boolean isTwoFactAuthEnabled;
    private String session;
}
