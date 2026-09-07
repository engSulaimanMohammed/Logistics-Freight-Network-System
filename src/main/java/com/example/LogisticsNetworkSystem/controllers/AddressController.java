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

    /** Creates an address from the validated DTO and associates it with a customer. */
    @PostMapping("/add")
    public AddressDTO add(@Valid @RequestBody AddressDTO dto) {
        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setPostalCode(dto.getPostalCode());
        address.setCountry(dto.getCountry());
        return AddressDTO.convertToDTO(addressService.addAddress(address, dto.getCustomerId()));
    }

    /** Returns all stored addresses as response DTOs. */
    @GetMapping("/getAll")
    public List<AddressDTO> getAll() {
        return AddressDTO.convertToDTO(addressService.getAllAddresss());
    }

    /** Retrieves the address identified by the {@code id} path variable. */
    @GetMapping("/getById/{id}")
    public AddressDTO getById(@PathVariable Long id) {
        return AddressDTO.convertToDTO(addressService.getById(id));
    }

    /** Updates the address identified by {@code id} with the validated request DTO. */
    @PutMapping("/update/{id}")
    public AddressDTO update(@PathVariable Long id, @Valid @RequestBody AddressDTO dto) {
        return AddressDTO.convertToDTO(addressService.updateAddress(id, dto.getStreet(), dto.getCity(), dto.getPostalCode(), dto.getCountry(), dto.getCustomerId()));
    }

    /** Deletes the address identified by {@code id} and returns its DTO representation. */
    @DeleteMapping("/delete/{id}")
    public AddressDTO delete(@PathVariable Long id) {
        AddressDTO dto = AddressDTO.convertToDTO(addressService.getById(id));
        addressService.deleteById(id);
        return dto;
    }
}
