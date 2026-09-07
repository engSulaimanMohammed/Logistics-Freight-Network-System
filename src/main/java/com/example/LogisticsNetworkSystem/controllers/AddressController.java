package com.example.LogisticsNetworkSystem.controllers;

import com.example.LogisticsNetworkSystem.dtos.AddressDTO;
import com.example.LogisticsNetworkSystem.entities.Address;
import com.example.LogisticsNetworkSystem.services.AddressService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/** Exposes REST operations for managing customer addresses. */
@RestController
@RequestMapping("/address")
public class AddressController {

    /** Coordinates address persistence and lookup through the service layer. */
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/add")
    // Creates an address from the request DTO and associates it with a customer.
    public AddressDTO add(@Valid @RequestBody AddressDTO dto) {
        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setPostalCode(dto.getPostalCode());
        address.setCountry(dto.getCountry());
        return AddressDTO.convertToDTO(addressService.addAddress(address, dto.getCustomerId()));
    }

    @GetMapping("/getAll")
    // Retrieves all addresses and converts the entities to response DTOs.
    public List<AddressDTO> getAll() {
        return AddressDTO.convertToDTO(addressService.getAllAddresss());
    }

    @GetMapping("/getById/{id}")
    // Retrieves an address by its unique identifier.
    public AddressDTO getById(@PathVariable Long id) {
        return AddressDTO.convertToDTO(addressService.getById(id));
    }

    @PutMapping("/update/{id}")
    // Updates the address identified by the path variable using the request DTO.
    public AddressDTO update(@PathVariable Long id, @Valid @RequestBody AddressDTO dto) {
        return AddressDTO.convertToDTO(addressService.updateAddress(id, dto.getStreet(), dto.getCity(), dto.getPostalCode(), dto.getCountry(), dto.getCustomerId()));
    }

    @DeleteMapping("/delete/{id}")
    // Removes the selected address and returns its DTO representation.
    public AddressDTO delete(@PathVariable Long id) {
        AddressDTO dto = AddressDTO.convertToDTO(addressService.getById(id));
        addressService.deleteById(id);
        return dto;
    }
}
