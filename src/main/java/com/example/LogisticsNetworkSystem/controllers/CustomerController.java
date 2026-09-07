package com.example.LogisticsNetworkSystem.controllers;

import com.example.LogisticsNetworkSystem.dtos.CustomerDTO;
import com.example.LogisticsNetworkSystem.dtos.CustomerStatsDTO;
import com.example.LogisticsNetworkSystem.entities.Customer;
import com.example.LogisticsNetworkSystem.services.CustomerService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/** Exposes REST operations for customer management and customer statistics. */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/add")
    public CustomerDTO add(@Valid @RequestBody CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setType(dto.getType());
        return CustomerDTO.convertToDTO(customerService.addCustomer(customer));
    }

    @GetMapping("/getAll")
    public List<CustomerDTO> getAll() {
        return CustomerDTO.convertToDTO(customerService.getAllCustomers());
    }

    @GetMapping("/getById/{id}")
    public CustomerDTO getById(@PathVariable Long id) {
        return CustomerDTO.convertToDTO(customerService.getById(id));
    }

    @PutMapping("/update/{id}")
    public CustomerDTO update(@PathVariable Long id, @Valid @RequestBody CustomerDTO dto) {
        return CustomerDTO.convertToDTO(customerService.updateCustomer(id, dto.getName(), dto.getEmail(), dto.getPhoneNumber(), dto.getType()));
    }

@GetMapping("/stats/{id}")
public CustomerStatsDTO getStats(@PathVariable Long id) {
    return customerService.getStats(id);
}

    @DeleteMapping("/delete/{id}")
    public CustomerDTO delete(@PathVariable Long id) {
        CustomerDTO dto = CustomerDTO.convertToDTO(customerService.getById(id));
        customerService.deleteById(id);
        return dto;
    }
}
