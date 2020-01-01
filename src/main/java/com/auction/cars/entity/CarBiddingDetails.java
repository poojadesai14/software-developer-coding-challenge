package com.auction.cars.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@javax.persistence.Table(name = "car_bidding_details")
public class CarBiddingDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "car_bidding_id")
    private int carBiddingId;


    @Column(name = "bidding_amount")
    private BigDecimal biddingAmount;

    @Column(name = "last_updated_time")
    private Date lastUpdatedTs;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bidder_id")
    private CustomerDetails customerDetails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "auction_car_id")
    private CarDetails carDetails;






}