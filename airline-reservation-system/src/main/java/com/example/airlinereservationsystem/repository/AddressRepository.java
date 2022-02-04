package com.example.airlinereservationsystem.repository;


import com.example.airlinereservationsystem.domain.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/**
 * @author Abdi Wako Jilo
 */
@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
    Address getAddressById(String ID);
}
