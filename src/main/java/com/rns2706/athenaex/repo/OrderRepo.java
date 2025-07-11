package com.rns2706.athenaex.repo;


import com.rns2706.athenaex.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order,Long> {

    public List<Order> findByUserId(Long userId);
}
