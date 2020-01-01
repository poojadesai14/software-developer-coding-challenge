package com.auction.cars.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@javax.persistence.Table(name = "car_details")
public class CarDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "car_id")
    private int carId;


    @Column(name = "car_name")
    private String carName;

    @Column(name = "car_price")
    private BigDecimal carMarketPrice;

    @Column(name = "status")
    private String carStatus;

    @Column(name = "mileage")
    private BigDecimal mileage;


    @Column(name = "location")
    private String location;

}
