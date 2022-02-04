package com.example.airlinereservationsystem.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
/**
 * @author Abdi Wako Jilo
 */
@Data
@NoArgsConstructor
@Entity
@RequiredArgsConstructor
@Table(name = "flight_instances")
public class FlightInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(name = "departure_date")
    @NonNull
    private LocalDate departureDate;

    @Column(name = "arrival_date")
    @NonNull
    private LocalDate arrivalDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NonNull
    @JoinColumn(name = "flight_id")
    private Flight flight;


}
