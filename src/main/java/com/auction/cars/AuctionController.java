package com.auction.cars;

import com.auction.cars.entity.domain.AuctionDetails;
import com.auction.cars.exception.AuctionApplicationException;
import com.auction.cars.service.AuctionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auction")
@Slf4j
public class AuctionController {

    @Autowired
    AuctionService auctionService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, value = "/{details}")
    public ResponseEntity createCarAuction(
            @Valid @RequestBody AuctionDetails auctionDetails)
        throws AuctionApplicationException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        auctionService.createAuctionEntry( auctionDetails );
        stopWatch.stop();
        return new ResponseEntity<>( HttpStatus.CREATED );
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/max_bid/car")
    public ResponseEntity<BigDecimal> getMaxBidAmount(
            @RequestParam(required = false) String carId)
            throws AuctionApplicationException {
        BigDecimal amount = auctionService.getMaxBidAmount(carId);
        return new ResponseEntity(amount, HttpStatus.OK );
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/history")
    public ResponseEntity<List<AuctionDetails>> getAuctionHistory(
            @RequestParam(required = false) String carId)
            throws AuctionApplicationException {

        return new ResponseEntity( auctionService.getAuctionHistory( carId ), HttpStatus.OK );
    }
}
