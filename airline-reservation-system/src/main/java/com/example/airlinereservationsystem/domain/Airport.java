package com.example.airlinereservationsystem.domain;


import lombok.*;

import javax.persistence.*;
/**
 * @author Abdi Wako Jilo
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Airport {
    @Id @GeneratedValue
    private  Long id;
    @NonNull
    @Column(nullable = false, unique = true)
    private String code;
    @NonNull
    @Column(nullable = false)
    private String name;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address address;

}
