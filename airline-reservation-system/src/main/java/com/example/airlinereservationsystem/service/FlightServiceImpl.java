package com.example.airlinereservationsystem.service;

import com.example.airlinereservationsystem.domain.*;
import com.example.airlinereservationsystem.dto.FlightDto;
import com.example.airlinereservationsystem.util.exception.ResourceNotFoundException;
import com.example.airlinereservationsystem.repository.FlightRespository;
import com.example.airlinereservationsystem.service.interfaces.AirlineService;
import com.example.airlinereservationsystem.service.interfaces.AirportService;
import com.example.airlinereservationsystem.service.interfaces.FlightService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
/**
 * @author Abdi Wako Jilo
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FlightServiceImpl implements FlightService {
    @Autowired
    private FlightRespository flightRespository;

    @Autowired
    private AirlineService airlineService;

    @Autowired
    private AirportService airportService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Flight> findAll() {
        return flightRespository.findAll();
    }

    @Override
    public Page<Flight> findAll(Pageable pageable) {
        return flightRespository.findAll(pageable);
    }

    @Override
    public Flight findById(Long id) {
        return flightRespository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight with id " + id + " not found"));
    }

    @Override
    public Flight addFlight(FlightDto flightDto) {
        Airline airline = airlineService.getAirlineById(flightDto.getAirline());
        Airport departureAirport = airportService.getById(flightDto.getDepartureAirport());
        Airport arrivalAirport = airportService.getById(flightDto.getArrivalAirport());
        Flight flight = modelMapper.map(flightDto, Flight.class);
        flight.setAirline(airline);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureAirport(departureAirport);
        return flightRespository.save(flight);
    }

    @Override
    public List<Flight> findSomeByAirports(String departure, String destination) {
        List<Flight> flights = flightRespository.findAll()
                .stream()
                .filter(flight -> flight.getDepartureAirport().getCode().equals(departure))
                .filter(flight -> flight.getArrivalAirport().getCode().equals(destination))
                .collect(Collectors.toList());

        if (flights.isEmpty())
            throw new ResourceNotFoundException("Flight with the departure " + departure + " and destination " + destination + " not found");

        return flights;
    }

    @Override
    public List<Flight> findSomeByAirlineCode(String code) {
        List<Flight> flights = flightRespository.findAll()
                .stream()
                .filter(flight -> flight.getAirline().getCode().equals(code))
                .collect(Collectors.toList());
        if (flights.isEmpty())
            throw new ResourceNotFoundException("Flight with airline code " + code + " not found");
        return flights;
    }

    @Override
    public Flight updateFlightProperty(Long id, FlightDto flightDto) {
        Flight flight = flightRespository.findById(id).orElseThrow(() -> new ResourceNotFoundException("flight with id " + id + " not found for update"));
        Airline airline = airlineService.getAirlineById(flightDto.getAirline());
        Airport arrivalAirport = airportService.getById(flightDto.getArrivalAirport());
        Airport departureAirport = airportService.getById(flightDto.getDepartureAirport());

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(flightDto, flight);
        flight.setAirline(airline);
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flightRespository.flush();
        return flight;
    }

    @Override
    public void removeFlight(Long id) {
        Flight flight = flightRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("flight with id " + id + " not found for deletion"));

        flightRespository.delete(flight);
    }

    @Override
    public Flight getOneFlightByAirlineCode(String code) {
        return flightRespository.findAll()
                                .stream()
                                .filter(flight -> flight.getAirline().getCode().equals(code))
                                .findFirst()
                                .orElseThrow(() -> new ResourceNotFoundException("Flight with airline code " + code + " not found"));
    }

    @Override
    public List<Flight> getOneFlightByDepartureAirportCode(String code){
        List<Flight> flights =  flightRespository.findAll()
                .stream()
                .filter(flight -> flight.getDepartureAirport().getCode().equals(code))
                .collect(Collectors.toList());
        if (flights.isEmpty())
            throw new ResourceNotFoundException("Departure airport with code " + code + " not found");
        return flights;
    }
    @Override
    public List<Flight> searchFlights(String searchParam) {
        List<Flight> flights = findAll();
        if (searchParam == null) {
            return flights;
        }
        return flights.stream()
                .filter(flight -> flight.getAirline().getName().toLowerCase().equals(searchParam.toLowerCase()) ||
                        flight.getArrivalAirport().getName().toLowerCase().equals(searchParam.toLowerCase()) ||
                        flight.getDepartureAirport().getName().toLowerCase().equals(searchParam.toLowerCase()) ||
                        flight.getNumber().toString().toLowerCase().equals(searchParam.toLowerCase())
                ).collect(Collectors.toList());
    }

}
