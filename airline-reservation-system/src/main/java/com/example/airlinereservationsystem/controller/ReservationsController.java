package com.example.airlinereservationsystem.controller;


import com.example.airlinereservationsystem.domain.*;
import com.example.airlinereservationsystem.dto.ConfirmationDto;
import com.example.airlinereservationsystem.dto.ReservationsDto;
import com.example.airlinereservationsystem.service.interfaces.*;
import com.example.airlinereservationsystem.service.ReservationsServiceImp;
import com.example.airlinereservationsystem.util.ResponseHandler;
import com.example.airlinereservationsystem.util.security.JwtUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
/**
 * @author Abdi Wako Jilo
 */
@RestController
@RequestMapping
public class ReservationsController {
    Logger logger = LoggerFactory.getLogger(TicketsController.class);
    @Autowired
    ReservationsService reservationsService;
    @Autowired
    UserService userService;
    @Autowired
    TicketsService ticketsService;

    @Autowired
    FlightInstanceService flightinstanceService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ReservationsServiceImp reservationsServiceImpl;



    @PostMapping("/reservations")
    public  ResponseEntity<?> addReservation(@RequestBody ReservationsDto reservationsDto){

        Reservations reservations = new Reservations();
        // get passanger user
        final Optional<User> user = userService.findUserByID(reservationsDto.getUserId());
        user.orElseThrow(()-> new UsernameNotFoundException("No user found: "));
        reservations.setUser(user.get());

        // get a performer user
        User performedUser = getUserFromAuth();
        reservations.setPerformedUser(performedUser);


        long [] ids = reservationsDto.getFlightInstanceIds();
        List<FlightInstance > flightInstances = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            final Optional<FlightInstance> flightInstance = flightinstanceService.findById(ids[i]);
            flightInstance.orElseThrow(() -> new UsernameNotFoundException("No reservation found: "));
            flightInstances.add(flightInstance.get());
        }
       reservations.setFlightInstances(flightInstances);

        Reservations respone = reservationsService.addReservation(reservations);
        if ( respone != null){
            return  ResponseHandler.respond("Successfully added a reservation!", HttpStatus.OK, respone);
        } else {
            return  ResponseHandler.respond("Null entities found", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reservations/confirm")
    public  ResponseEntity<?> confirmReservations(@RequestBody ConfirmationDto confirmationDto){
        // check if that reservation exists
        final Optional<Reservations> reservation = reservationsService.findReservationsByID(confirmationDto.getReservationId());
        reservation.orElseThrow(()-> new UsernameNotFoundException("No reservation found: "));
        Reservations reservationObj = reservation.get();

        // check if the reservation performerId belongs to that user
        User user = getUserFromAuth();
        if(user.getID() != reservationObj.getPerformedUser().getID())
            return  ResponseHandler.respond("Can't perform that action", HttpStatus.FORBIDDEN);

        // fetch flight instances for that reservation
        List<FlightInstance> list = reservationObj.getFlightInstances();

        List<Tickets>  ticketsResponse = new ArrayList<>();

        for(int i = 0; i < list.size(); i++) {
            Tickets ticket = new Tickets();
            ticket.setReservation(reservationObj);
            ticket.setFlightInstance(list.get(i));
            ticket.setNumber(genrateRandomString("numeric"));
            ticket.setReservationCode(genrateRandomString("alpha"));
            ticketsResponse.add( ticketsService.addTicket(ticket));
        }
        //finResponse.setTickets(ticketsResponse);
        if ( ticketsResponse.size() != 0 ){
            return  ResponseHandler.respond("Successfully added a ticket!", HttpStatus.OK);
        } else {
            return  ResponseHandler.respond("Null entities found", HttpStatus.BAD_REQUEST);
        }

    }

    public static String genrateRandomString(String type) {
        String SALTCHARS = "";
        int size  = 0;
        if(type.equals("alpha")){
            SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            size = 6;
        }
        else{
            SALTCHARS = "0123456789";
            size = 20;
        }
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < size) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    @RequestMapping(value="reservations", method = RequestMethod.GET)
    public @ResponseBody List<Reservations> getReservations(){
        User user = getUserFromAuth();
        return reservationsService.getAllByUserId(user.getID());
    }

    @RequestMapping(value="/reservations/{id}", method = RequestMethod.GET)
    public  @ResponseBody Reservations getAReservationByUserId(@PathVariable Long id){
        User user = getUserFromAuth();
        return reservationsService.getAReservationByUserId(id, user.getID());
    }
    @RequestMapping(value="/reservations/tickets/{id}", method = RequestMethod.GET)
    public  @ResponseBody List<Tickets> getTickets(@PathVariable Long id){
        User user = getUserFromAuth();
        return ticketsService.getTickets(id, user.getID());
    }

    @DeleteMapping(path="/reservations/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable long id){
        final Optional<Reservations> reservation = reservationsService.findReservationsByID(id);
        if (reservation.isEmpty()){
            return ResponseEntity.badRequest().body("Reservation with given id doesn't exist");
        }
        reservationsService.deleteReservation(id);
        return ResponseEntity.ok().body("Reservation with code : " + id + " was deleted");
    }

    public User getUserFromAuth(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        final Optional<User> user = userService.findUserByUsername(userName);
        user.orElseThrow(()-> new UsernameNotFoundException("No user found: "));
        return user.get();
    }


}
    
