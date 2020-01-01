package com.auction.cars.service;

import com.auction.cars.entity.CarBiddingDetails;
import com.auction.cars.entity.CarDetails;
import com.auction.cars.entity.CustomerDetails;
import com.auction.cars.entity.domain.AuctionDetails;
import com.auction.cars.exception.AuctionApplicationException;
import com.auction.cars.repository.AuctionRepository;
import com.auction.cars.repository.CarRepository;
import com.auction.cars.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    UserRepository userRepository;

    @Setter
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void createAuctionEntry(AuctionDetails auctionDetails) throws AuctionApplicationException {
        try {
            CustomerDetails customerDetails = userRepository.findCustomer( Integer.valueOf(
                    auctionDetails.getUserId() ) );
            CarDetails carDetails = carRepository.findCarDetails( Integer.valueOf( auctionDetails.getCarId() ) );
            CarBiddingDetails carBiddingDetails = new CarBiddingDetails();
            carBiddingDetails.setBiddingAmount(auctionDetails.getBiddingAmount());
            carBiddingDetails.setCarDetails( carDetails );
            carBiddingDetails.setCustomerDetails( customerDetails );
            carBiddingDetails.setLastUpdatedTs( new Date() );
            //check if the incoming bidding amount is greater than existing max bidding amount for that car
            if (auctionRepository.findAuctionHistory(carDetails.getCarId()).size()>0 ) {
                if (auctionDetails.getBiddingAmount().compareTo(
                        auctionRepository.fetchMaxAuctionAmount(
                                carDetails.getCarId())) == 1) {
                    auctionRepository.save(carBiddingDetails);
                } else {
                    log.info("Bidding Amount is lesser/equal to the current max bidding amount for carId:{}",
                            carDetails.getCarId());
                    throw new AuctionApplicationException("",400);
                }
            } else {
                if (auctionDetails.getBiddingAmount().compareTo(carDetails.getCarMarketPrice()) == 1) {
                    auctionRepository.save( carBiddingDetails );
                } else {
                    log.info("Bidding Amount is lesser/equal to the current market amount for carId:{}",
                            carDetails.getCarId());
                    throw new AuctionApplicationException("",400);
                }
            } log.info( "The auction details successfully saved for card Id:{}",auctionDetails.getCarId() );
        } catch (Exception ex) {
            throw new AuctionApplicationException("Something went wrong while creating auction data",500);
        }
    }

    @Override
    public BigDecimal getMaxBidAmount(String carId) {
        BigDecimal maxAuctionAmount = auctionRepository.fetchMaxAuctionAmount(Integer.valueOf(carId));
        log.info( "The max auction amount for carId:{} is maxAuctionAmount:{}",carId,maxAuctionAmount);
        return maxAuctionAmount.setScale(2,BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public List<AuctionDetails> getAuctionHistory(String carId) {
        List<AuctionDetails>auctionDetails = new ArrayList<>();
        List<CarBiddingDetails> auctionHistory= auctionRepository.findAuctionHistory(Integer.valueOf(carId));
        if(!auctionHistory.isEmpty()) {
            for (CarBiddingDetails historyDetail : auctionHistory) {
                AuctionDetails auctionDetail = new AuctionDetails();
                auctionDetail.setBiddingAmount( historyDetail.getBiddingAmount() );
                auctionDetail.setCarId( String.valueOf( historyDetail.getCarDetails().getCarId() ) );
                auctionDetail.setUserId( String.valueOf( historyDetail.getCustomerDetails().getUserId() ) );
                auctionDetail.setCarName( historyDetail.getCarDetails().getCarName() );
                auctionDetail.setUserName( historyDetail.getCustomerDetails().getUserName() );
                auctionDetail.setUserEmail( historyDetail.getCustomerDetails().getUserMailId() );
                auctionDetail.setUserNumber( historyDetail.getCustomerDetails().getUserContactNumber() );
                auctionDetails.add( auctionDetail );
            }
        } else {
            throw  new AuctionApplicationException("No Auction History found",500);
        }
        log.info("The size of history data for the carId:{}",carId);
        return auctionDetails;
    }
}

