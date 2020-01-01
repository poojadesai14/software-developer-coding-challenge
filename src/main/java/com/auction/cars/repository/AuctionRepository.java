package com.auction.cars.repository;

import com.auction.cars.entity.CarBiddingDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AuctionRepository extends CrudRepository<CarBiddingDetails,Integer> {

    @Query(value = "Select * FROM car_bidding_details  WHERE  auction_car_id = :auction_car_id",nativeQuery = true)
    public List<CarBiddingDetails> findAuctionHistory(@Param("auction_car_id") Integer auctionCarId);

    @Query(value = "SELECT max(bidding_amount) FROM car_bidding_details  WHERE auction_car_id = :auction_car_id",
            nativeQuery = true)
    public BigDecimal fetchMaxAuctionAmount (@Param("auction_car_id") Integer auctionCarId);

}
