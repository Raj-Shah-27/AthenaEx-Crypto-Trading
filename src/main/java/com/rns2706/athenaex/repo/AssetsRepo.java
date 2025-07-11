package com.rns2706.athenaex.repo;

import com.rns2706.athenaex.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetsRepo extends JpaRepository<Asset,Long> {
    public List<Asset> findByUserId(Long userId);

    Asset findByUserIdAndCoinId(Long userId, String coinId);

    Asset findByIdAndUserId(Long assetId, Long userId);
}
