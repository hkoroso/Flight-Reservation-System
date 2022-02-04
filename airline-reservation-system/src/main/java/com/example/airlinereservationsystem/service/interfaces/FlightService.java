package com.example.airlinereservationsystem.service.interfaces;

import com.example.airlinereservationsystem.domain.Flight;
import com.example.airlinereservationsystem.dto.FlightDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
/**
 * @author Abdi Wako Jilo
 */
public interface FlightService {
    List<Flight> findAll();
    Page<Flight> findAll(Pageable pageable);
    Flight findById(Long id);
    Flight addFlight(FlightDto flightDto);
    List<Flight> findSomeByAirports(String departure, String destination);
    Flight updateFlightProperty(Long id, FlightDto flightDto);
    void removeFlight(Long id);
    Flight getOneFlightByAirlineCode(String code);
    List<Flight> getOneFlightByDepartureAirportCode(String code);
    List<Flight> findSomeByAirlineCode(String code);
    List<Flight> searchFlights(String searchParam);
}
