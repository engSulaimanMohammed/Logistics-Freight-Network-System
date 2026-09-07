package com.example.LogisticsNetworkSystem.services;

import com.example.LogisticsNetworkSystem.entities.Customer;
import com.example.LogisticsNetworkSystem.entities.Invoice;
import com.example.LogisticsNetworkSystem.entities.Shipment;
import com.example.LogisticsNetworkSystem.exceptions.ResourceNotFoundException;
import java.util.Date;
import java.util.List;

import com.example.LogisticsNetworkSystem.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final ShipmentService shipmentService;
    private final CustomerService customerService;

    public InvoiceService(
            InvoiceRepository invoiceRepository,
            ShipmentService shipmentService,
            CustomerService customerService) {
        this.invoiceRepository = invoiceRepository;
        this.shipmentService = shipmentService;
        this.customerService = customerService;
    }

    private void validateData(Double amount, String status, Date issuedDate) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be blank");
        }
        if (status.length() > 255) throw new IllegalArgumentException("Status cannot exceed 255 characters");
        if (issuedDate == null) throw new IllegalArgumentException("Issued date cannot be null");
        if (issuedDate.after(new Date())) throw new IllegalArgumentException("Issued date cannot be in the future");
    }

    public Invoice addInvoice(Invoice invoice, Long shipmentId, Long customerId) {
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice cannot be null");
        }
        validateData(invoice.getAmount(), invoice.getStatus(), invoice.getIssuedDate());
        invoice.setShipment(shipmentService.getById(shipmentId));
        invoice.setCustomer(customerService.getById(customerId));
        invoice.setActive(true);
        invoice.setCreatedDate(new Date());
        return invoiceRepository.save(invoice);
    }

    public Invoice generateInvoice(Invoice invoice, Long shipmentId) {
        if (invoice == null) throw new IllegalArgumentException("Invoice cannot be null");
        Shipment shipment = shipmentService.getById(shipmentId);
        if (!"Delivered".equalsIgnoreCase(shipment.getStatus())) {
            throw new IllegalArgumentException("Invoice cannot be generated before shipment is delivered");
        }
        validateData(invoice.getAmount(), "Unpaid", invoice.getIssuedDate());
        Customer customer = shipment.getCustomer();
        invoice.setShipment(shipment);
        invoice.setCustomer(customer);
        invoice.setStatus("Unpaid");
        invoice.setActive(true);
        invoice.setCreatedDate(new Date());
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .filter(Invoice::isActive)
                .toList();
    }

    public Invoice getById(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("Invoice ID must be greater than zero");
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));
        if (!invoice.isActive()) throw new ResourceNotFoundException("Invoice not found with id: " + id);
        return invoice;
    }

    public Invoice updateInvoice(Long id, Double amount, String status, Date issuedDate,
                                 Long shipmentId, Long customerId) {
        validateData(amount, status, issuedDate);
        Invoice invoice = getById(id);
        invoice.setAmount(amount);
        invoice.setStatus(status);
        invoice.setIssuedDate(issuedDate);
        invoice.setShipment(shipmentService.getById(shipmentId));
        invoice.setCustomer(customerService.getById(customerId));
        invoice.setUpdatedDate(new Date());
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getUnpaidInvoicesByCustomer(Long customerId) {
        customerService.getById(customerId);
        return invoiceRepository.findUnpaidInvoicesByCustomer(customerId);
    }

    public boolean deleteById(Long id) {
        Invoice invoice = getById(id);
        invoice.setActive(false);
        invoice.setUpdatedDate(new Date());
        invoiceRepository.save(invoice);
        return true;
    }
}
