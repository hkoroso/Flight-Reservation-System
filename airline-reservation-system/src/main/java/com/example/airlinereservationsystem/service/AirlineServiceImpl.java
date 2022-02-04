package com.example.airlinereservationsystem.service;

import com.example.airlinereservationsystem.domain.Airline;
import com.example.airlinereservationsystem.util.exception.ResourceNotFoundException;
import com.example.airlinereservationsystem.repository.AirlineRepository;
import com.example.airlinereservationsystem.service.interfaces.AirlineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
/**
 * @author Abdi Wako Jilo
 */
@Service @RequiredArgsConstructor @Transactional @Slf4j
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository repository;

    @Override
    public List<Airline> getAllAirlines() {
        return repository.findAll();
    }

    @Override
    public Airline getAirlineByCode(String code) {
        return repository.findByCode(code);
    }

    @Override
    public Airline getAirlineById(Long id) {
        return repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Airline with id " + id + " not found"));
    }

    @Override
    public void addAirline(Airline airline) {
        repository.save(airline);
    }

    @Override
    public void updateAirline(Airline airline) {
        Optional<Airline> existingAirline = Optional.ofNullable(repository.findByCode(airline.getCode()));
        if (!existingAirline.isPresent()){
            throw new IllegalStateException("Airline with given code does not exist (code: " + airline.getCode()+")");
        }
        existingAirline.get().setCode(airline.getCode());
        existingAirline.get().setHistory(airline.getHistory());
    }
    @Override
    public void deleteAirline(String code) {
        Optional<Airline> existingAirline = Optional.ofNullable(repository.findByCode(code));
        if (!existingAirline.isPresent()){
            throw new IllegalStateException("Airline with given code does not exist (code: " + code +")");
        }
        repository.deleteByCode(code);
    }

}
