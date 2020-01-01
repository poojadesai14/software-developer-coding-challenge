package com.auction.cars.service;

import com.auction.cars.entity.CarBiddingDetails;
import com.auction.cars.entity.CarDetails;
import com.auction.cars.entity.CustomerDetails;
import com.auction.cars.entity.domain.AuctionDetails;
import com.auction.cars.exception.AuctionApplicationException;
import com.auction.cars.repository.AuctionRepository;
import com.auction.cars.repository.CarRepository;
import com.auction.cars.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuctionServiceTest {

    @InjectMocks
    AuctionServiceImpl auctionService;

    @Mock
    AuctionRepository auctionRepository;

    @Mock
    CarRepository carRepository;

    @Mock
    UserRepository userRepository;

    @Test
    public void shouldReturnMaxBiddingAmountWhenCarDetailsIsGiven(){
        String cardId = "123";
        when(auctionRepository.fetchMaxAuctionAmount(
                Integer.valueOf(cardId))).thenReturn(BigDecimal.valueOf(1234566.00));
        BigDecimal amount = auctionService.getMaxBidAmount(cardId);
        Assert.assertEquals(BigDecimal.valueOf(1234566.00),amount);


    }

    @Test
    public  void shouldReturnHistoryWhenCarIdsIsGiven(){
        List<CarBiddingDetails> carBiddingDetailsList = new ArrayList<>();
        String cardId = "123";
        CarDetails carDetails = new CarDetails();
        carDetails.setCarId(Integer.valueOf(123));
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setUserId(Integer.valueOf(12));
        CarBiddingDetails carBiddingDetails = new CarBiddingDetails();
        carBiddingDetails.setBiddingAmount(BigDecimal.TEN);
        carBiddingDetails.setLastUpdatedTs(new Date());
        carBiddingDetails.setCarBiddingId(123);
        carBiddingDetails.setCarDetails(carDetails);
        carBiddingDetails.setCustomerDetails(customerDetails);

        CarDetails carDetails1 = new CarDetails();
        carDetails1.setCarId(Integer.valueOf(123));
        CustomerDetails customerDetails1 = new CustomerDetails();
        customerDetails1.setUserId(Integer.valueOf(12));
        CarBiddingDetails carBiddingDetails1 = new CarBiddingDetails();
        carBiddingDetails1.setBiddingAmount(BigDecimal.TEN);
        carBiddingDetails1.setLastUpdatedTs(new Date());
        carBiddingDetails1.setCarBiddingId(13);
        carBiddingDetails1.setCarDetails(carDetails);
        carBiddingDetails1.setCustomerDetails(customerDetails);

        carBiddingDetailsList.add(carBiddingDetails);
        carBiddingDetailsList.add(carBiddingDetails1);
        when(auctionRepository.findAuctionHistory(Integer.valueOf(123))).thenReturn(carBiddingDetailsList);
        List<AuctionDetails>auctionDetailsList = auctionService.getAuctionHistory(cardId);

        Assert.assertEquals(2,auctionDetailsList.size());
    }

    @Test(expected = AuctionApplicationException.class)
    public void shouldThrowExceptionWhenBiddingAmountIsLesserThanExistingAmount(){
        AuctionDetails auctionDetails = new AuctionDetails();
        auctionDetails.setUserId("345");
        auctionDetails.setCarId("987");
        auctionDetails.setBiddingAmount(BigDecimal.ZERO);
        List<CarBiddingDetails> carBiddingDetails = new ArrayList<>();
        when(auctionRepository.findAuctionHistory(
                Integer.valueOf(auctionDetails.getCarId()))).thenReturn(carBiddingDetails);
        when(auctionRepository.fetchMaxAuctionAmount(
                Integer.valueOf(auctionDetails.getCarId()))).thenReturn(BigDecimal.TEN);
        auctionService.createAuctionEntry(auctionDetails);

    }

    @Test(expected = AuctionApplicationException.class)
    public void shouldThrowExceptionWhenBiddingAmountIsLesserThanMarketAmount(){
        AuctionDetails auctionDetails = new AuctionDetails();
        auctionDetails.setUserId("345");
        auctionDetails.setCarId("987");
        auctionDetails.setBiddingAmount(BigDecimal.ZERO);
        CarDetails carDetails = new CarDetails();
        carDetails.setCarMarketPrice(BigDecimal.TEN);
        when(auctionRepository.findAuctionHistory(
                Integer.valueOf(auctionDetails.getCarId()))).thenReturn(null);
        auctionService.createAuctionEntry(auctionDetails);

    }


    @Test(expected = AuctionApplicationException.class)
    public  void shouldThrowExceptionWhenNoHistoryPresentforCar(){
        List<CarBiddingDetails> carBiddingDetailsList = new ArrayList<>();
        String cardId = "123";
        when(auctionRepository.findAuctionHistory(Integer.valueOf(123))).thenReturn(carBiddingDetailsList);
        List<AuctionDetails>auctionDetailsList = auctionService.getAuctionHistory(cardId);
    }



}
