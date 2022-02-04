package com.example.airlinereservationsystem.service.interfaces;

import com.example.airlinereservationsystem.domain.Airline;
import com.example.airlinereservationsystem.domain.Airport;

import java.util.List;
/**
 * @author Abdi Wako Jilo
 */
public interface AirportService {
    List<Airport> getAllAirports();
    Airport getAirportByCode(String code);
    Airport getById(Long id);

    void addAirport(Airport airport);
    void updateAirport(Airport airport);

    void deleteAirport(String code);
}
