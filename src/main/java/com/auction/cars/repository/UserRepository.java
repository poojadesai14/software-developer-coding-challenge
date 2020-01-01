package com.auction.cars.repository;

import com.auction.cars.entity.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<CustomerDetails,Integer> {

    @Query("SELECT c FROM CustomerDetails c WHERE userId = ?1" )
    public CustomerDetails findCustomer(Integer userId);


}