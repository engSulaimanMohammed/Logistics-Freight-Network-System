package com.example.LogisticsNetworkSystem.services;

import com.example.LogisticsNetworkSystem.dtos.CustomerStatsDTO;
import com.example.LogisticsNetworkSystem.entities.Customer;
import com.example.LogisticsNetworkSystem.exceptions.ResourceNotFoundException;
import com.example.LogisticsNetworkSystem.repositories.CustomerRepository;
import java.util.Date;
import java.util.List;

import com.example.LogisticsNetworkSystem.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final InvoiceRepository invoiceRepository;

    public CustomerService(
            CustomerRepository customerRepository,
            InvoiceRepository invoiceRepository) {
        this.customerRepository = customerRepository;
        this.invoiceRepository = invoiceRepository;
    }

    private void validateCustomerData(String name, String email, String phoneNumber, String type) {
        if (name == null
                || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (name.length() > 255) {
            throw new IllegalArgumentException("Name cannot exceed 255 characters");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be blank");
        }
        if (email.length() > 255) {
            throw new IllegalArgumentException("Email cannot exceed 255 characters");
        }
        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Email must be valid");
        }
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("PhoneNumber cannot be blank");
        }
        if (phoneNumber.length() > 255) {
            throw new IllegalArgumentException("PhoneNumber cannot exceed 255 characters");
        }
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Type cannot be blank");
        }
        if (type.length() > 255) {
            throw new IllegalArgumentException("Type cannot exceed 255 characters");
        }
    }

    public Customer addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        validateCustomerData(
                customer.getName(), customer.getEmail(), customer.getPhoneNumber(), customer.getType());
        customer.setActive(true);
        customer.setCreatedDate(new Date());
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll().stream().filter(Customer::isActive).toList();
    }

    public Customer getById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Customer ID must be greater than zero");
        }
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        if (!customer.isActive()) {
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        }
        return customer;
    }

    public Customer updateCustomer(Long id, String name, String email, String phoneNumber, String type) {
        validateCustomerData(name, email, phoneNumber, type);
        Customer customer = getById(id);
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customer.setType(type);
        customer.setUpdatedDate(new Date());
        return customerRepository.save(customer);
    }

public CustomerStatsDTO getStats(Long customerId) {
    getById(customerId);
    Double total = invoiceRepository.totalInvoicedAmountByCustomer(customerId);
    return CustomerStatsDTO.builder()
            .customerId(customerId)
            .totalInvoicedAmount(total == null ? 0.0 : total)
            .build();
}

    public boolean deleteById(Long id) {
        Customer customer = getById(id);
        customer.setActive(false);
        customer.setUpdatedDate(new Date());
        customerRepository.save(customer);
        return true;
    }
}
