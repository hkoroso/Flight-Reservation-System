package com.example.airlinereservationsystem.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
/**
 * @author Abdi Wako Jilo
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SecondaryTable(name = "AIRLINE_HISTORY", pkJoinColumns ={
        @PrimaryKeyJoinColumn(name = "AIRPORT_ID", referencedColumnName = "id")
} )
public class Airline {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false,unique = true)
    private String code;
    @Column(nullable = false)
    private String name;
    @Column(table = "AIRLINE_HISTORY")
    private String history;

    public Airline(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
