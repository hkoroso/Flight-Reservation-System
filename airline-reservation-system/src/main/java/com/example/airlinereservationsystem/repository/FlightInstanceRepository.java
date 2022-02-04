package com.example.airlinereservationsystem.repository;

import com.example.airlinereservationsystem.domain.Flight;
import com.example.airlinereservationsystem.domain.FlightInstance;
import com.example.airlinereservationsystem.domain.User;
import com.example.airlinereservationsystem.dto.FlightDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
/**
 * @author Abdi Wako Jilo
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public interface FlightInstanceRepository extends JpaRepository<FlightInstance, Long> {
    @Query("SELECT fi FROM FlightInstance fi WHERE fi.flight.id = :id")
    public Page<FlightInstance> findAllPerFlight(@Param("id") Long id, Pageable pageable);

    @Query("SELECT fi FROM FlightInstance fi JOIN fi.flight f WHERE f.departureAirport.code = :departureAirport and f.arrivalAirport.code = :arrivalAirport and fi.departureDate= :date")
    public Page<FlightInstance> findAllBetweenTwoDestinationsOnADate(@Param("departureAirport")String departureAirport, @Param("arrivalAirport")String arrivalAirport,  @Param("date")LocalDate date, Pageable pageable);

    Optional<FlightInstance> findById(long id);

}
