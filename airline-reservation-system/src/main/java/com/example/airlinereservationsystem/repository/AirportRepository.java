package com.example.airlinereservationsystem.repository;

import com.example.airlinereservationsystem.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @author Abdi Wako Jilo
 */

public interface AirportRepository extends JpaRepository<Airport, Long> {
    Airport findByCode(String code);

    void deleteByCode(String code);
}
