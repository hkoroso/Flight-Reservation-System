package com.example.airlinereservationsystem.repository;

import com.example.airlinereservationsystem.domain.Flight;
import com.example.airlinereservationsystem.domain.Reservations;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
/**
 * @author Abdi Wako Jilo
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public interface ReservationsRepository extends CrudRepository <Reservations, Integer>, JpaSpecificationExecutor<Reservations> {

    Optional<Reservations> findByID(long id);

    @Query("SELECT r FROM Reservations r WHERE r.user.ID = :userId OR r.performedUser.ID = :userId")
    public List<Reservations> getAllByUserId(@Param("userId") long userId);

    @Query("SELECT r FROM Reservations r WHERE  (r.user.ID = :userId OR r.performedUser.ID = :userId) AND r.ID = :id")
    public Reservations getAReservationByUserId(@Param("id") long id, @Param("userId") long userId);

    void deleteByID(long id);

    @Query("FROM Reservations r")
     List<Reservations> getAllReservation();

}
