package com.example.airlinereservationsystem.service;

import java.util.List;
import java.util.Optional;

import com.example.airlinereservationsystem.service.interfaces.ReservationsService;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.airlinereservationsystem.domain.Reservations;
import com.example.airlinereservationsystem.repository.ReservationsRepository;
import org.springframework.stereotype.Service;
/**
 * @author Abdi Wako Jilo
 */
@Service
public class ReservationsServiceImp implements ReservationsService {
    @Autowired
    ReservationsRepository reservationsRepository;
	@Override
	public  Reservations addReservation(Reservations reservation) {
		return reservationsRepository.save( reservation);

	}
	@Override
	public Optional<Reservations> findReservationsByID(long id) {
        return reservationsRepository.findByID(id);

	}

	@Override
	public List<Reservations> getAllByUserId(long userId) {
		return reservationsRepository.getAllByUserId(userId);
	}

	@Override
	public Reservations getAReservationByUserId(Long id, long userId) {

		return reservationsRepository.getAReservationByUserId(id, userId);
	}

	@Override
	public void deleteReservation(long id) {
		reservationsRepository.deleteByID(id);
	}

	@Override
	public List<Reservations> getAllReservation() {
		return  reservationsRepository.getAllReservation();
	}
}
