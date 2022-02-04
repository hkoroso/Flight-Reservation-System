package com.example.airlinereservationsystem.dto;

import com.example.airlinereservationsystem.domain.Flight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
/**
 * @author Abdi Wako Jilo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightInstanceDto {
    private Long id;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private Flight flight;
}
