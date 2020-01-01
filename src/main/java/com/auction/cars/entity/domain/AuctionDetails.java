package com.auction.cars.entity.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AuctionDetails {

    @NotNull
    private String userId;
    private String userName;
    private String userEmail;
    private String userNumber;
    @NotNull
    private String carId;
    private String carName;
    @NotNull
    private BigDecimal biddingAmount;
}
