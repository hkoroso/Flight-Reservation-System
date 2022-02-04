package com.example.airlinereservationsystem.controller;

import com.example.airlinereservationsystem.domain.Address;
import com.example.airlinereservationsystem.domain.Airline;
import com.example.airlinereservationsystem.domain.Airport;
import com.example.airlinereservationsystem.domain.Flight;
import com.example.airlinereservationsystem.dto.AirportDto;
import com.example.airlinereservationsystem.service.interfaces.AddressService;
import com.example.airlinereservationsystem.service.interfaces.AirlineService;
import com.example.airlinereservationsystem.service.interfaces.AirportService;
import com.example.airlinereservationsystem.service.interfaces.FlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
/**
 * @author Abdi Wako Jilo
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping
public class AirportController {
    private final AirportService airportService;
    private final FlightService flightService;
    private final AddressService addressService;
    private final ModelMapper modelMapper;

    private int codeLength = 3;

    @GetMapping("/airports")
    public ResponseEntity<List<Airport>> getAllAirports(){
        log.info("get all airports");
        return ResponseEntity.ok().body(airportService.getAllAirports());
    }
    @GetMapping("/airports/{code}")
    public ResponseEntity<Airport> getAirportByCode(@PathVariable String code){
        log.info("get airport by code {}", code);
        return ResponseEntity.ok().body(airportService.getAirportByCode(code));
    }

    @PostMapping("/admin/airports")
    public ResponseEntity<?> addAirport(@RequestBody AirportDto airportDto){
        log.info("[INFO] AirPORT DTO {}", airportDto);
        if( airportDto.getCode().isEmpty() || airportDto.getName().isEmpty() || airportDto.getCode().length()!=codeLength){
            return ResponseEntity.badRequest().body("Given attributes are not valid");
        }
        Airport airport = modelMapper.map(airportDto, Airport.class);
        Optional<Address> addressOptional = Optional.ofNullable(addressService.getAddressById(airportDto.getAddress_id()));
        if (!addressOptional.isPresent()){
            return ResponseEntity.badRequest().body("Address with given ID doesn't exist");
        }
        airport.setAddress(addressOptional.get());
        airportService.addAirport(airport);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/admin/airports/{code}")
    public ResponseEntity<?> updateAirport(@RequestBody AirportDto airportDto, @PathVariable String code){
        if( airportDto.getCode().isEmpty() || airportDto.getName().isEmpty() || airportDto.getCode().length()!=codeLength){
            return ResponseEntity.badRequest().body("Given attributes are not valid");
        }
        Optional<Address> addressOptional = Optional.ofNullable(addressService.getAddressById(airportDto.getAddress_id()));
        if (!addressOptional.isPresent()){
            return ResponseEntity.badRequest().body("Address with given ID doesn't exist");
        }
        Optional<Airport> optionalAirport = Optional.ofNullable(airportService.getAirportByCode(code));
        if (!optionalAirport.isPresent()){
            return ResponseEntity.badRequest().body("Airport with given code doesn't exist");
        }
        Airport airport = optionalAirport.get();
        airport.setCode(airportDto.getCode());
        airport.setName(airportDto.getName());
        airport.setAddress(addressService.getAddressById(airportDto.getAddress_id()));
        airportService.updateAirport(airport);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/airports/{airportCode}/flights")
    public ResponseEntity<List<Flight>> getAllFlightsByAirportCode(@PathVariable String airportCode){
        return ResponseEntity.ok().body(flightService.getOneFlightByDepartureAirportCode(airportCode));
    }

    @DeleteMapping("/admin/airports/{code}")
    public ResponseEntity<?> deleteAirport(@PathVariable String code){
        Optional<Airport> optionalAirport = Optional.ofNullable(airportService.getAirportByCode(code));
        if (!optionalAirport.isPresent()){
            return ResponseEntity.badRequest().body("Airport with given code doesn't exist");
        }
        airportService.deleteAirport(code);
        return ResponseEntity.ok().body("Airport with code : " + code + " deleted");
    }
}
