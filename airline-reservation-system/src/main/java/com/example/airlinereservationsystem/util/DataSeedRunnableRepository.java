package com.example.airlinereservationsystem.util;

import com.example.airlinereservationsystem.domain.*;
import com.example.airlinereservationsystem.repository.FlightInstanceRepository;
import com.example.airlinereservationsystem.repository.FlightRespository;
import com.example.airlinereservationsystem.service.interfaces.UserService;
import com.example.airlinereservationsystem.util.constant.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
/**
 * @author Abdi Wako Jilo
 */
@Component
@Transactional(propagation = Propagation.REQUIRED)
public class DataSeedRunnableRepository implements CommandLineRunner {
    @Autowired
    FlightInstanceRepository flightInstanceRepository;

    @Autowired
    FlightRespository flightRespository;

    @Autowired
    UserService userService;

    @Override
    public void run(String... args) throws Exception {
        runForFlightAndFlightInstance();
    }

    /***
     * This method creates admin user and flights
     */
    private void  runForFlightAndFlightInstance(){

        // Admin address
        Address address = new Address();
        address.setCity("Fairfield");
        address.setState("IOWA");
        address.setZipCode("52557");
        address.setStreet("1000th N 4th Street");

        // Admin user
        List<UserRole> userRoles = new ArrayList<>();
        UserRole userRole = new UserRole();
        userRole.setRoleName(Roles.ROLE_ADMIN);
        userRoles.add(userRole);

        User user = new User();
        user.setPassword("abdi");
        user.setRole(userRoles);
        user.setDataOfBirth("23/09/1995");
        user.setEmail("ajilo@miu.edu");
        user.setFirstName("Abdi");
        user.setLastName("Jilo");
        user.setResidenceAddress(address);
        user.setUsername("abdi");

        // Agent user
        List<UserRole> userRolesAgent = new ArrayList<>();
        UserRole agent = new UserRole();
        agent.setRoleName(Roles.ROLE_AGENT);
        userRolesAgent.add(agent);

        User userAgent = new User();
        userAgent.setPassword("babishu");
        userAgent.setRole(userRolesAgent);
        userAgent.setDataOfBirth("23/09/1995");
        userAgent.setEmail("babishu@gmail.com");
        userAgent.setFirstName("Babishu");
        userAgent.setLastName("Jilo");
        userAgent.setResidenceAddress(address);
        userAgent.setUsername("babishu");

        /* Flights */
        List<Flight> flights = new ArrayList<>();
        flights.add( new Flight(120012L, 66, new Airline("AL-1", "Airline-name-1"), new Airport("AP1", "airport-1"), new Airport("AP14", "airport-6"), LocalTime.of(12,00,00),LocalTime.of(12,00,00 )));
        flights.add( new Flight(51210L, 55, new Airline("AL-2", "Airline-name-2"), new Airport("AP2", "airport-2"), new Airport("AP15", "airport-7"), LocalTime.of(12,00,00),LocalTime.of(12,00,00 )));
        flights.add( new Flight(45762L, 44, new Airline("AL-3", "Airline-name-3"), new Airport("AP3", "airport-3"), new Airport("AP16", "airport-8"), LocalTime.of(12,00,00),LocalTime.of(12,00,00 )));
        flights.add( new Flight(156720L, 33, new Airline("AL-4", "Airline-name-4"), new Airport("AP4", "airport-4"), new Airport("AP17", "airport-9"), LocalTime.of(12,00,00),LocalTime.of(12,00,00 )));
        flights.add( new Flight(205670L, 77, new Airline("AL-5", "Airline-name-5"), new Airport("AP5", "airport-5"), new Airport("AP18", "airport-10"), LocalTime.of(12,00,00),LocalTime.of(12,00,00 )));


        /* Flight Instances */
        // For flight0
        List<FlightInstance> flightInstances = new ArrayList<>();
        flightInstances.add(new FlightInstance(LocalDate.of(2021, 11, 18), LocalDate.of(2020, 06, 9), flights.get(0)));
        flightInstances.add( new FlightInstance(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), flights.get(0)));
        flightInstances.add( new FlightInstance(LocalDate.of(2020, 6, 8), LocalDate.of(2020, 6, 8), flights.get(0)));
        flightInstances.add( new FlightInstance(LocalDate.of(2020, 9, 20), LocalDate.of(2020, 9, 21), flights.get(0)));
        flightInstances.add( new FlightInstance(LocalDate.of(2020, 12, 8), LocalDate.of(2020, 12, 9), flights.get(0)));
        flightInstances.add( new FlightInstance(LocalDate.of(2020, 11, 8), LocalDate.of(2020, 11, 8), flights.get(0)));

        //for flight1
        flightInstances.add( new FlightInstance(LocalDate.of(2020, 1, 8), LocalDate.of(2020, 1, 9), flights.get(1)));
        flightInstances.add( new FlightInstance(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), flights.get(1)));
        flightInstances.add( new FlightInstance(LocalDate.of(2020, 3, 8), LocalDate.of(2020, 3, 8), flights.get(1)));
        flightInstances.add( new FlightInstance(LocalDate.of(2020, 10, 20), LocalDate.of(2020, 10, 21), flights.get(1)));
        flightInstances.add( new FlightInstance(LocalDate.of(2020, 12, 8), LocalDate.of(2020, 12, 9), flights.get(1)));
        flightInstances.add( new FlightInstance(LocalDate.of(2020, 11, 8), LocalDate.of(2020, 11, 8), flights.get(1)));


        userService.signup(user);
        userService.signup(userAgent);
        flightRespository.saveAll(flights);
        flightInstanceRepository.saveAll(flightInstances);
    }
}
