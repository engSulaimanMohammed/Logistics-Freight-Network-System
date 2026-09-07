package com.example.LogisticsNetworkSystem.repositories;

import com.example.LogisticsNetworkSystem.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
