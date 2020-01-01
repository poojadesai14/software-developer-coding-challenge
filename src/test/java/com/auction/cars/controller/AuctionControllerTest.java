package com.auction.cars.controller;

import com.auction.cars.AuctionController;
import com.auction.cars.entity.domain.AuctionDetails;
import com.auction.cars.service.AuctionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class AuctionControllerTest {

    @InjectMocks
    AuctionController auctionController;

    @Mock
    AuctionService auctionService;


    @Test
    public void givenValidAuctionWhenRequestToCreateThenReturnCREATED() {
        doNothing().when( auctionService ).createAuctionEntry( any() );
        AuctionDetails auctionDetails = new AuctionDetails();
        auctionDetails.setBiddingAmount( BigDecimal.valueOf( 12367.00 ) );
        auctionDetails.setCarId("12");
        auctionDetails.setUserId( "13" );
        ResponseEntity responseEntity = auctionController
                .createCarAuction( auctionDetails );
        assertNotNull( responseEntity );
        assertEquals( HttpStatus.CREATED, responseEntity.getStatusCode() );

    }

    @Test
    public void shouldReturnMaxBiddingAmountWhenCarNameIsGiven() {
        Mockito.when( auctionService.getMaxBidAmount( anyString() ) ).thenReturn( BigDecimal.valueOf( 123456.00 ) );
        AuctionDetails auctionDetails = new AuctionDetails();
        auctionDetails.setCarId( "10" );
        ResponseEntity<BigDecimal> responseEntity = auctionController
                .getMaxBidAmount(auctionDetails.getCarId());
        assertNotNull( responseEntity );
        assertEquals( BigDecimal.valueOf( 123456.00 ), responseEntity.getBody() );
        assertEquals( HttpStatus.OK, responseEntity.getStatusCode() );

    }


    @Test
    public void shouldReturnBiddingHistoryWhenCarIdIsGiven() {
        AuctionDetails auctionDetails = new AuctionDetails();
        auctionDetails.setUserId( "123" );
        List<AuctionDetails> auctionDetailsList = new ArrayList<>();
        Mockito.when( auctionService.getAuctionHistory( anyString() ) ).thenReturn( auctionDetailsList );
        ResponseEntity<List<AuctionDetails>> responseEntity = auctionController
                .getAuctionHistory( "678" );
        assertNotNull( responseEntity );
        assertEquals( HttpStatus.OK, responseEntity.getStatusCode() );

    }
}
