package com.example.airlinereservationsystem.controller;

import com.example.airlinereservationsystem.domain.Airline;
import com.example.airlinereservationsystem.dto.AirlineDto;
import com.example.airlinereservationsystem.service.interfaces.AirlineService;
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
@RequestMapping
@Slf4j
public class AirlineController {
    private final AirlineService AirLineService;
    private final ModelMapper modelMapper;

    private int codeLength = 3;

    @GetMapping("/airlines")
    public ResponseEntity<List<Airline>> getAllAirports(){
        log.info("[INFO] get all airlines");
        return ResponseEntity.ok().body(AirLineService.getAllAirlines());
    }
    @GetMapping("/airlines/{code}")
    public ResponseEntity<Airline> getAirportByCode(@PathVariable String code){
        log.info("[INFO] get airline by code {}", code);
        return ResponseEntity.ok().body(AirLineService.getAirlineByCode(code));
    }
    @PostMapping("/admin/airlines")
    public ResponseEntity<?> addAirline(@RequestBody AirlineDto airlineDto){
        if (airlineDto.getCode().isEmpty() || airlineDto.getName().isEmpty() || airlineDto.getCode().length()!=codeLength){
            return ResponseEntity.badRequest().body("Given attributes are not valid");
        }
        Airline airline = modelMapper.map(airlineDto, Airline.class);
        AirLineService.addAirline(airline);
        log.info("[INFO] adding Airline {}", airline);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/admin/airlines/{code}")
    public ResponseEntity<?> updateAirline(@RequestBody AirlineDto airlineDto, @PathVariable String code){
        if (airlineDto.getCode().isEmpty() || airlineDto.getName().isEmpty() || airlineDto.getCode().length()!=codeLength){
            return ResponseEntity.badRequest().body("Given attributes are not valid");
        }
        Optional<Airline> airlineOptional = Optional.ofNullable(AirLineService.getAirlineByCode(code));
        if (!airlineOptional.isPresent()){
            return ResponseEntity.badRequest().body("Airline with given code doesn't exist");
        }
        airlineOptional.get().setHistory(airlineDto.getHistory());
        airlineOptional.get().setCode(airlineDto.getCode());
        airlineOptional.get().setName(airlineDto.getName());
        AirLineService.updateAirline(airlineOptional.get());
        log.info("[INFO] updating airline with code {}",  airlineOptional.get().getCode());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/airlines/{code}")
    public ResponseEntity<?> deleteAirport(@PathVariable String code){
        Optional<Airline> optionalAirline = Optional.ofNullable(AirLineService.getAirlineByCode(code));
        log.info("[optionalAirline] optionalAirline {}", optionalAirline.toString());
        if (!optionalAirline.isPresent()){
            return ResponseEntity.badRequest().body("Airline with given code doesn't exist");
        }
        AirLineService.deleteAirline(code);
        return ResponseEntity.ok().body("Airline with code : " + code + " was deleted");
    }
}