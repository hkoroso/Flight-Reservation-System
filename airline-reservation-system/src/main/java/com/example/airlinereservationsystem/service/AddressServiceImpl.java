package com.example.airlinereservationsystem.service;

import com.example.airlinereservationsystem.domain.Address;
import com.example.airlinereservationsystem.repository.AddressRepository;
import com.example.airlinereservationsystem.service.interfaces.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * @author Abdi Wako Jilo
 */

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> getAllAddresses() {
        return (List<Address>) addressRepository.findAll();
    }

    @Override
    public Address getAddressById(long id) {
        return addressRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteAddress(long id) {
        addressRepository.deleteById(id);
    }

    @Override
    public Address updateAddress(Address updateAddress, long id) {
        Address address = addressRepository.findById(id).orElse(null);
        address.setStreet(updateAddress.getStreet());
        address.setZipCode(updateAddress.getZipCode());
        address.setCity(updateAddress.getCity());
        address.setState(updateAddress.getState());
        return addressRepository.save(address);
    }

    @Override
    public void addAddress(Address address) {

    }

    public Address getAddressById (Long ID) {
        return addressRepository.findById(ID).get();
    }
}
