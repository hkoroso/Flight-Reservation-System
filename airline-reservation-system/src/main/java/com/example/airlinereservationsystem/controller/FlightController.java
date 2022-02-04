package com.example.airlinereservationsystem.controller;

import com.example.airlinereservationsystem.domain.Flight;
import com.example.airlinereservationsystem.dto.FlightDto;
import com.example.airlinereservationsystem.service.FlightServiceImpl;
import com.example.airlinereservationsystem.util.ResponseHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
/**
 * @author Abdi Wako Jilo
 */
@RestController
public class FlightController {

    @Autowired
    FlightServiceImpl flightService;

    @GetMapping(value = "/flights")
    public List<Flight> findAll() {
        return flightService.findAll();
    }

    @GetMapping(value = "/flights", params = "paged=true")
    public ResponseEntity<?> findAll(Pageable pageable) {
        return ResponseHandler
                .respond("Success", HttpStatus.OK, flightService.findAll(pageable));
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseHandler
                .respond("Success", HttpStatus.OK, flightService.findById(id));
    }

    @GetMapping(value = "/flights", params = {"dep", "dest"})
    @ResponseBody
    public ResponseEntity<?> findSomeByAirports(@RequestParam(name = "dep", required = false) String departureAirport,
                                                @RequestParam(name = "dest", required = false) String destinationAirport) {

        return ResponseHandler
                .respond("Success", HttpStatus.OK, flightService.findSomeByAirports(departureAirport, destinationAirport));
    }

    @GetMapping(value = "/flights/airline/{code}")
    @ResponseBody
    public ResponseEntity<?> findSomeByAirline(@PathVariable String code) {
        return ResponseHandler
                .respond("Success", HttpStatus.OK, flightService.findSomeByAirlineCode(code));
    }

    @PostMapping(path = "/admin/flights", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addFlight(@RequestBody FlightDto flightDto) {
        Flight flight = flightService.addFlight(flightDto);
        if (flight != null) {
            return ResponseHandler.respond("Successfully added a flight!", HttpStatus.OK, flight);
        } else {
            return ResponseHandler.respond("Null entities found", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/admin/flights/{id}", consumes = "application/json")
    public ResponseEntity<?> updateFlight(@PathVariable Long id, @RequestBody FlightDto flightDto) {
        Flight flight = flightService.updateFlightProperty(id, flightDto);
        return ResponseHandler.respond("Successfully updated a flight!", HttpStatus.ACCEPTED, flight);
    }

    @DeleteMapping(path = "/admin/flights/{id}")
    public ResponseEntity<?> deleteFlight(@PathVariable Long id) {
        flightService.removeFlight(id);
        return ResponseHandler.respond("Successfully deleted a flight!", HttpStatus.ACCEPTED);
    }

    @GetMapping(value = {"/flights/list"})
    public ModelAndView listFlights(){
        ModelAndView modelAndView= new ModelAndView();
        List<Flight> availableFlights= flightService.findAll();
        modelAndView.addObject("flights",availableFlights);
        modelAndView.addObject("searchString","");
        modelAndView.addObject("flightsCount",availableFlights.size());
        modelAndView.setViewName("flight/flightlists");
        return modelAndView;
    }


    @GetMapping(value = {"/searchFlight"})
    public ModelAndView searchFlights(@RequestParam String searchString) {
        ModelAndView modelAndView = new ModelAndView();
        List<Flight> flights = flightService.searchFlights(searchString);
        modelAndView.addObject("flights",flights);
        modelAndView.addObject("searchString", searchString);
        modelAndView.addObject("flightsCount", flights.size());
        modelAndView.setViewName("flight/flightlists");
        return modelAndView;
    }
    @GetMapping(value = {"/bookFlight/booking/{flightId}"})
    public String bookFlight( @PathVariable Long flightId, Model model) {
        Flight flight = flightService.findById(flightId);
        if (flight != null) {
            model.addAttribute("flight", flight);
            return "flight/bookFlight";
        }
        return "flight/flightLists";
    }
}

