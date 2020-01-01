package com.auction.cars.repository;

import com.auction.cars.entity.CarDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<CarDetails,Integer> {

    @Query("SELECT c FROM CarDetails c WHERE car_id = ?1")
    public CarDetails findCarDetails(Integer carId);
}


