package com.example.airlinereservationsystem.service.interfaces;

import com.example.airlinereservationsystem.domain.Airline;

import java.util.List;
import java.util.Optional;
/**
 * @author Abdi Wako Jilo
 */
public interface AirlineService {
    List<Airline> getAllAirlines();
    Airline getAirlineByCode(String code);
    Airline getAirlineById(Long Id);
    void addAirline(Airline airline);
    void updateAirline(Airline airline);

    void deleteAirline(String code);
}
