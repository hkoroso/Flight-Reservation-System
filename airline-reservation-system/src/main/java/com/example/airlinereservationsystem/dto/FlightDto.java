package com.example.airlinereservationsystem.dto;

import lombok.*;

import java.time.LocalTime;
/**
 * @author Abdi Wako Jilo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDto {
    @NonNull
    private Long id;
    @NonNull
    private Long number;
    @NonNull
    private Integer numberOfSeats;
    @NonNull
    private Long airline;
    @NonNull
    private Long departureAirport;
    @NonNull
    private Long arrivalAirport;
    @NonNull
    private LocalTime departureTime;
    @NonNull
    private LocalTime arrivalTime;

}
