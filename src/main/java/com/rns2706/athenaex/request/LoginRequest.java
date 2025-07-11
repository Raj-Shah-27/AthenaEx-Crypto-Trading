package com.rns2706.athenaex.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
