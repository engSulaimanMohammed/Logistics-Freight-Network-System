package com.example.LogisticsNetworkSystem.controllers;

import com.example.LogisticsNetworkSystem.dtos.InvoiceDTO;
import com.example.LogisticsNetworkSystem.entities.Invoice;
import com.example.LogisticsNetworkSystem.services.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Exposes REST operations for invoice creation, lookup, and payment status workflows. */
@RestController
@RequestMapping("/invoice")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    private Invoice fromDTO(InvoiceDTO dto) {
        Invoice invoice = new Invoice();
        invoice.setAmount(dto.getAmount());
        invoice.setStatus(dto.getStatus());
        invoice.setIssuedDate(dto.getIssuedDate());
        return invoice;
    }

    @PostMapping("/add")
    public InvoiceDTO add(@Valid @RequestBody InvoiceDTO dto) {
        return InvoiceDTO.convertToDTO(invoiceService.addInvoice(
                fromDTO(dto), dto.getShipmentId(), dto.getCustomerId()));
    }

    @PostMapping("/generate")
    public InvoiceDTO generate(@Valid @RequestBody InvoiceDTO dto) {
        return InvoiceDTO.convertToDTO(invoiceService.generateInvoice(fromDTO(dto), dto.getShipmentId()));
    }

    @GetMapping("/getAll")
    public List<InvoiceDTO> getAll() {
        return InvoiceDTO.convertToDTO(invoiceService.getAllInvoices());
    }

    @GetMapping("/getById/{id}")
    public InvoiceDTO getById(@PathVariable Long id) {
        return InvoiceDTO.convertToDTO(invoiceService.getById(id));
    }

    @PutMapping("/update/{id}")
    public InvoiceDTO update(@PathVariable Long id, @Valid @RequestBody InvoiceDTO dto) {
        return InvoiceDTO.convertToDTO(invoiceService.updateInvoice(
                id, dto.getAmount(), dto.getStatus(), dto.getIssuedDate(),
                dto.getShipmentId(), dto.getCustomerId()));
    }

    @GetMapping("/unpaidByCustomer/{customerId}")
    public List<InvoiceDTO> unpaidByCustomer(@PathVariable Long customerId) {
        return InvoiceDTO.convertToDTO(invoiceService.getUnpaidInvoicesByCustomer(customerId));
    }

    @DeleteMapping("/delete/{id}")
    public InvoiceDTO delete(@PathVariable Long id) {
        InvoiceDTO dto = InvoiceDTO.convertToDTO(invoiceService.getById(id));
        invoiceService.deleteById(id);
        return dto;
    }
}
