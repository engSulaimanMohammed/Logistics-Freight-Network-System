package com.example.LogisticsNetworkSystem.services;

import com.example.LogisticsNetworkSystem.entities.Address;
import com.example.LogisticsNetworkSystem.entities.Customer;
import com.example.LogisticsNetworkSystem.exceptions.ResourceNotFoundException;
import com.example.LogisticsNetworkSystem.repositories.AddressRepository;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final CustomerService customerService;

    public AddressService(
            AddressRepository addressRepository,
            CustomerService customerService) {
        this.addressRepository = addressRepository;
        this.customerService = customerService;
    }

    private void validateAddressData(String street, String city, String postalCode, String country) {
        if (street == null
                || street.trim().isEmpty()) {
            throw new IllegalArgumentException("Street cannot be blank");
        }
        if (street.length() > 255) {
            throw new IllegalArgumentException("Street cannot exceed 255 characters");
        }
        if (city == null
                || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be blank");
        }
        if (city.length() > 255) {
            throw new IllegalArgumentException("City cannot exceed 255 characters");
        }
        if (postalCode == null || postalCode.trim().isEmpty()) {
            throw new IllegalArgumentException("PostalCode cannot be blank");
        }
        if (postalCode.length() > 255) {
            throw new IllegalArgumentException("PostalCode cannot exceed 255 characters");
        }
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be blank");
        }
        if (country.length() > 255) {
            throw new IllegalArgumentException("Country cannot exceed 255 characters");
        }
    }

    public Address addAddress(Address address, Long customerId) {
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }
        validateAddressData(
                address.getStreet(), address.getCity(), address.getPostalCode(), address.getCountry());
        Customer customer = customerService.getById(customerId);
        address.setCustomer(customer);
        address.setActive(true);
        address.setCreatedDate(new Date());
        return addressRepository.save(address);
    }

    public List<Address> getAllAddresss() {
        return addressRepository.findAll().stream()
                .filter(Address::isActive)
                .toList();
    }

    public Address getById(Long id) {
        if (id == null
                || id <= 0) {
            throw new IllegalArgumentException("Address ID must be greater than zero");
        }
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
        if (!address.isActive()) {
            throw new ResourceNotFoundException("Address not found with id: " + id);
        }
        return address;
    }

    public Address updateAddress(Long id, String street, String city, String postalCode, String country, Long customerId) {
        validateAddressData(street, city, postalCode, country);
        Address address = getById(id);
        Customer customer = customerService.getById(customerId);
        address.setStreet(street);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setCountry(country);
        address.setCustomer(customer);
        address.setUpdatedDate(new Date());
        return addressRepository.save(address);
    }

    public boolean deleteById(Long id) {
        Address address = getById(id);
        address.setActive(false);
        address.setUpdatedDate(new Date());
        addressRepository.save(address);
        return true;
    }
}
