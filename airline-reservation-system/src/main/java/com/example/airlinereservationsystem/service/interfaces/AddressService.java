package com.example.airlinereservationsystem.service.interfaces;

import com.example.airlinereservationsystem.domain.Address;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author Abdi Wako Jilo
 */
@Service
public interface AddressService {
     List<Address> getAllAddresses();
     Address getAddressById(long id);
     void deleteAddress(long id);
     Address updateAddress(Address address, long id);
     void addAddress(Address address);

}
