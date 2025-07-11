package com.rns2706.athenaex.service;
import com.rns2706.athenaex.domain.OrderType;
import com.rns2706.athenaex.model.*;

import java.util.List;

public interface OrderService {

    Order createOrder(User user, OrderItem orderItem, OrderType orderType);

    Order getOrderById(Long orderId);

    List<Order> getAllOrdersForUser(Long userId, String orderType,String assetSymbol);

    void cancelOrder(Long orderId);

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;

}
