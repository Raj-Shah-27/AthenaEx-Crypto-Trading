package com.rns2706.athenaex.repo;

import com.rns2706.athenaex.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItem,Long> {
}
