package com.auction.cars.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@javax.persistence.Table(name = "user_details")
public class CustomerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int userId;


    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_address")
    private String userAddress;

    @Column(name = "user_contact_number")
    private String userContactNumber;

    @Column(name = "user_email_id")
    private String userMailId;

}

