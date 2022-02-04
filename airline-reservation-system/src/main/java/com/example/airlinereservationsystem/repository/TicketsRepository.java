package com.example.airlinereservationsystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.airlinereservationsystem.domain.Reservations;
import com.example.airlinereservationsystem.domain.Tickets;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @author Abdi Wako Jilo
 */

@Repository
@Transactional(propagation = Propagation.REQUIRED)

public interface TicketsRepository  extends CrudRepository <Tickets, Integer>{


    @Query("SELECT distinct  t FROM Tickets t WHERE  t.reservation.ID = :id AND ( t.reservation.user.ID = :userId OR  t.reservation.performedUser.ID = :userId)")
    public List<Tickets> getTickets(@Param("id") long id, @Param("userId") long userId);

}
