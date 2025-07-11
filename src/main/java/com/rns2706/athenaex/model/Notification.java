package com.rns2706.athenaex.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private Long fromUserId;
    private Long toUserid;
    private Long amount;
    private String message;
}
