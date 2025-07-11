package com.rns2706.athenaex.service;

import com.rns2706.athenaex.model.*;
import com.rns2706.athenaex.repo.WatchlistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchlistServiceImpl implements WatchlistService{
    @Autowired
    private WatchlistRepo watchlistRepo;


    @Override
    public Watchlist findUserWatchlist(Long userId) throws Exception {
        Watchlist watchlist=watchlistRepo.findByUserId(userId);
        if(watchlist==null){
            throw new Exception("watch not found");
        }
        return watchlist;
    }

    @Override
    public Watchlist createWatchList(User user) {
        Watchlist watchlist=new Watchlist();
        watchlist.setUser(user);
        return watchlistRepo.save(watchlist);
    }

    @Override
    public Watchlist findById(Long id) throws Exception {
        Optional<Watchlist> optionalWatchlist = watchlistRepo.findById(id);
        if(optionalWatchlist.isEmpty()){
            throw new Exception("watch list not found");
        }
        return optionalWatchlist.get();
    }

    @Override
    public Coin addItemToWatchlist(Coin coin,User user) throws Exception {
        Watchlist watchlist=findUserWatchlist(user.getId());

        if(watchlist.getCoins().contains(coin)){
            watchlist.getCoins().remove(coin);
        }
        else watchlist.getCoins().add(coin);
        watchlistRepo.save(watchlist);
        return coin;
    }
}
