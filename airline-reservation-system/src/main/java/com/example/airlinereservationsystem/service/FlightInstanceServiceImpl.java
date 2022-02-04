package com.example.airlinereservationsystem.service;

import com.example.airlinereservationsystem.domain.Flight;
import com.example.airlinereservationsystem.domain.FlightInstance;
import com.example.airlinereservationsystem.dto.FlightInstanceDto;
import com.example.airlinereservationsystem.util.exception.ResourceNotFoundException;
import com.example.airlinereservationsystem.repository.FlightInstanceRepository;
import com.example.airlinereservationsystem.service.interfaces.FlightInstanceService;
import com.example.airlinereservationsystem.service.interfaces.FlightService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
/**
 * @author Abdi Wako Jilo
 */

@Service
public class FlightInstanceServiceImpl implements FlightInstanceService {

    @Autowired
    private FlightInstanceRepository instanceRepository;

    @Autowired
    private FlightService flightService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<FlightInstance> findAll(Pageable pageable) {
        return instanceRepository.findAll(pageable);
    }

    @Override
    public Page<FlightInstance> findAllPerFlight(Long id, Pageable pageable) {
        return instanceRepository.findAllPerFlight(id, pageable);
    }

    @Override
    public  FlightInstance findOnePerFlight(Long id, Long instanceId) {
        return instanceRepository.findAll()
                .stream()
                .filter(instance -> instance.getFlight().getId().equals(id))
                .filter(instance -> instance.getId().equals(instanceId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No isntnaces found"));

    }

    @Override
    public Page<FlightInstance> findAllBetweenTwoDestinationsOnADate(String departureAirport, String arrivalAirport, LocalDate date, Pageable pageable) {
        return instanceRepository.findAllBetweenTwoDestinationsOnADate(departureAirport, arrivalAirport, date, pageable);
    }

    @Override
    public Optional<FlightInstance> findById(long id) {
        return instanceRepository.findById(id);
    }

    public FlightInstance addOnaFlight(Long id, FlightInstanceDto flightInstanceDto){
        Flight flight = flightService.findById(id);

        if (flight == null)
            throw new ResourceNotFoundException( "flight with id " + id + " not found");

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        FlightInstance flightInstance = new FlightInstance();
        modelMapper.map(flightInstanceDto, flightInstance);
        flightInstance.setFlight(flight);
        flightInstance = instanceRepository.save(flightInstance);

        return flightInstance;
    }

    @Override
    public void removeInstance(Long id){
        FlightInstance instance = instanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight instance with id " + id + " not found"));
        instanceRepository.delete(instance);
    }
}
