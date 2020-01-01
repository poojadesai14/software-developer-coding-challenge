package com.auction.cars.repository;

import com.auction.cars.entity.CarBiddingDetails;
import com.auction.cars.entity.CarDetails;
import com.auction.cars.entity.CustomerDetails;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class AuctionRepositoryTest {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired private JdbcTemplate jdbcTemplate;

    @Test
    public void testSaveEmployee() {

        CarBiddingDetails biddingDetails = new CarBiddingDetails();
        biddingDetails.setBiddingAmount(BigDecimal.TEN);
        CarDetails carDetails = new CarDetails();
        carDetails.setCarId(234);
        biddingDetails.setCarDetails(carDetails);
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setUserId(123);
        biddingDetails.setCarBiddingId(456);
        biddingDetails.setCustomerDetails(customerDetails);
        biddingDetails.setLastUpdatedTs(new Date());

        auctionRepository.save(biddingDetails);
        Assert.assertNotNull(auctionRepository.findAll());

    }

}
