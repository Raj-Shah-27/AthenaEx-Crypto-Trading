package com.rns2706.athenaex.repo;

import com.rns2706.athenaex.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepo extends JpaRepository<Coin,String> {

}
