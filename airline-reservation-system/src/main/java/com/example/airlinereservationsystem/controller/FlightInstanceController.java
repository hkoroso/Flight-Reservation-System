package com.example.airlinereservationsystem.controller;

import com.example.airlinereservationsystem.domain.FlightInstance;
import com.example.airlinereservationsystem.dto.FlightDto;
import com.example.airlinereservationsystem.dto.FlightInstanceDto;
import com.example.airlinereservationsystem.service.interfaces.FlightInstanceService;
import com.example.airlinereservationsystem.service.interfaces.FlightService;
import com.example.airlinereservationsystem.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
/**
 * @author Abdi Wako Jilo
 */
@RestController
public class FlightInstanceController {
    @Autowired
    FlightService flightService;

    @Autowired
    FlightInstanceService instanceService;

    @GetMapping(value = "/flights/instances", params = "paged=true")
    public Page<FlightInstance> findAll(Pageable pageable){
        return instanceService.findAll(pageable);
    }

    @GetMapping(value = "/flights/{id}/instances", params = "paged=true")
    public Page<FlightInstance> findAllPerFlight(@PathVariable Long id, Pageable pageable){
        return instanceService.findAllPerFlight(id, pageable);
    }

    @GetMapping(value = "/flights/{id}/instances/{instanceId}")
    public ResponseEntity<?> findOnePerFlight(@PathVariable Long id, @PathVariable Long instanceId){
        return ResponseHandler.respond("Success", HttpStatus.OK, instanceService.findOnePerFlight(id, instanceId));
    }

    @GetMapping(value = "/flights/instances", params = {"paged=true", "dep", "dest", "date"})
    public Page<FlightInstance> findAllBetweenTwoDestinationsOnADate(@RequestParam(name = "dep", required = false)String departureAirport,
                                                                     @RequestParam(name = "dest", required = false) String arrivalAirport,
                                                                     @RequestParam(name = "date", required = false) String date,
                                                                     Pageable pageable){
        return instanceService.findAllBetweenTwoDestinationsOnADate(departureAirport, arrivalAirport, LocalDate.parse(date), pageable);
    }

    @PostMapping(value = "/admin/flights/{id}/instances", consumes = "application/json")
    public ResponseEntity<?> addOnaFlight(@PathVariable Long id, @RequestBody FlightInstanceDto flightInstanceDto){
        return  ResponseHandler.respond("Successfully added a flight instance!", HttpStatus.OK, instanceService.addOnaFlight(id, flightInstanceDto));
    }

    @DeleteMapping(value ="/flights/instances/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id){
        instanceService.removeInstance(id);
        return ResponseHandler.respond("Flight instnace deleted successfully ", HttpStatus.OK);
    }
}
