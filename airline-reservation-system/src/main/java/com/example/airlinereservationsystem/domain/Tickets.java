package com.example.airlinereservationsystem.domain;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
/**
 * @author Abdi Wako Jilo
 */
@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tickets {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID;
    private String number;
    private String reservationCode;
    private Date created;
    private Date updated;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "reservation_id", referencedColumnName = "ID")
    private Reservations reservation;

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "flight_instance_id", referencedColumnName = "ID")
    private FlightInstance flightInstance;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }
}
