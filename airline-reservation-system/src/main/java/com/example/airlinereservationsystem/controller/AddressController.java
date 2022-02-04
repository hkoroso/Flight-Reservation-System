package com.example.airlinereservationsystem.controller;


import com.example.airlinereservationsystem.domain.Address;
import com.example.airlinereservationsystem.service.interfaces.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @author Abdi Wako Jilo
 */
@RestController
@RequestMapping
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/addresses")
    public ResponseEntity<List<Address>> getAllAddresses(){
        return ResponseEntity.ok().body(addressService.getAllAddresses());
    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable long id){
        return ResponseEntity.ok().body( addressService.getAddressById(id));
    }

    @DeleteMapping("/admin/addresses/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable long id){
        addressService.deleteAddress(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/admin/addresses/{id}")
    public ResponseEntity<?> updateAddress(@RequestBody  Address address, @PathVariable  long id){
        addressService.updateAddress(address, id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/addresses")
    public  ResponseEntity<?> updateAddress(@RequestBody Address address){
        addressService.addAddress(address);
        return ResponseEntity.ok().build();
    }

}
