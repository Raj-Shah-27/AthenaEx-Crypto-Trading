package com.rns2706.athenaex.service;

import com.rns2706.athenaex.model.*;
import com.rns2706.athenaex.repo.AssetsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssetServiceImpl implements  AssetService {
    private final AssetsRepo assetRepo;

    @Autowired
    public AssetServiceImpl(AssetsRepo assetRepo) {
        this.assetRepo = assetRepo;
    }

    @Override
    public Asset createAsset(User user, Coin coin, double quantity) {
        Asset asset = new Asset();

        asset.setQuantity(quantity);
        asset.setBuyPrice(coin.getCurrentPrice());
        asset.setCoin(coin);
        asset.setUser(user);

        return assetRepo.save(asset);
    }

    public Asset getAssetById(Long assetId) {
        return assetRepo.findById(assetId)
                .orElseThrow(() -> new IllegalArgumentException("Asset not found"));
    }

    @Override
    public Asset getAssetByUserAndId(Long userId, Long assetId) {
        return assetRepo.findByIdAndUserId(assetId,userId);
    }

    @Override
    public List<Asset> getUsersAssets(Long userId) {
        return assetRepo.findByUserId(userId);
    }



    @Override
    public Asset updateAsset(Long assetId, double quantity) throws Exception {

        Asset oldAsset=getAssetById(assetId);
        if(oldAsset==null){
            throw new Exception("Asset not found...");
        }
        oldAsset.setQuantity(quantity+ oldAsset.getQuantity());

        return assetRepo.save(oldAsset);
    }

    @Override
    public Asset findAssetByUserIdAndCoinId(Long userId, String coinId) throws Exception {
        return assetRepo.findByUserIdAndCoinId(userId,coinId);
    }


    public void deleteAsset(Long assetId) {
        assetRepo.deleteById(assetId);
    }

}

