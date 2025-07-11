package com.rns2706.athenaex.request;

import com.rns2706.athenaex.domain.OrderType;
import lombok.Data;

@Data
public class CreateOrderRequest {
    private String coinId;
    private double quantity;
    private OrderType orderType;
}
