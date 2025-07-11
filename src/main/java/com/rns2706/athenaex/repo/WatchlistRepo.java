package com.rns2706.athenaex.repo;

import com.rns2706.athenaex.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchlistRepo extends JpaRepository<Watchlist,Long> {

    Watchlist findByUserId(Long userId);

}
