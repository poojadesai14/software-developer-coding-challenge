package com.auction.cars.service;

import com.auction.cars.entity.domain.AuctionDetails;

import java.math.BigDecimal;
import java.util.List;

public interface AuctionService {

    void createAuctionEntry(AuctionDetails auctionDetails);

    BigDecimal getMaxBidAmount(String carId);

    List<AuctionDetails> getAuctionHistory(String carId);
}
