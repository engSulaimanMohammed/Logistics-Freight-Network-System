package com.example.LogisticsNetworkSystem.repositories;

import com.example.LogisticsNetworkSystem.entities.Invoice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("SELECT i FROM Invoice i WHERE i.customer.id = :customerId AND LOWER(i.status) = 'unpaid' AND i.isActive = true")
    List<Invoice> findUnpaidInvoicesByCustomer(@Param("customerId") Long customerId);

    @Query("SELECT COALESCE(SUM(i.amount), 0) FROM Invoice i WHERE i.customer.id = :customerId AND i.isActive = true")
    Double totalInvoicedAmountByCustomer(@Param("customerId") Long customerId);
}
