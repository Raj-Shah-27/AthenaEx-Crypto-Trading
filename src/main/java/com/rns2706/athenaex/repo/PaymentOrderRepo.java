package com.rns2706.athenaex.repo;

import com.rns2706.athenaex.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRepo extends JpaRepository<PaymentOrder,Long> {
}
