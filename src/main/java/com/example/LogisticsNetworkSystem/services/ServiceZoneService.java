package com.example.LogisticsNetworkSystem.services;

import com.example.LogisticsNetworkSystem.entities.ServiceZone;
import com.example.LogisticsNetworkSystem.exceptions.ResourceNotFoundException;
import java.util.Date;
import java.util.List;

import com.example.LogisticsNetworkSystem.repositories.ServiceZoneRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceZoneService {

    private final ServiceZoneRepository serviceZoneRepository;

    public ServiceZoneService(ServiceZoneRepository serviceZoneRepository) {
        this.serviceZoneRepository = serviceZoneRepository;
    }

    private void validateServiceZoneData(
            String name,
            String region,
            Double baseRate) {
        if (name == null
                || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (name.length() > 255) {
            throw new IllegalArgumentException("Name cannot exceed 255 characters");
        }
        if (region == null
                || region.trim().isEmpty()) {
            throw new IllegalArgumentException("Region cannot be blank");
        }
        if (region.length() > 255) {
            throw new IllegalArgumentException("Region cannot exceed 255 characters");
        }
        if (baseRate == null || baseRate <= 0) {
            throw new IllegalArgumentException("BaseRate must be greater than zero");
        }
    }

    public ServiceZone addServiceZone(ServiceZone serviceZone) {
        if (serviceZone == null) {
            throw new IllegalArgumentException("ServiceZone cannot be null");
        }
        validateServiceZoneData(serviceZone.getName(), serviceZone.getRegion(), serviceZone.getBaseRate());
        serviceZone.setActive(true);
        serviceZone.setCreatedDate(new Date());
        return serviceZoneRepository.save(serviceZone);
    }

    public List<ServiceZone> getAllServiceZones() {
        return serviceZoneRepository.findAll().stream().filter(ServiceZone::isActive).toList();
    }

    public ServiceZone getById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ServiceZone ID must be greater than zero");
        }
        ServiceZone serviceZone = serviceZoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ServiceZone not found with id: " + id));
        if (!serviceZone.isActive()) {
            throw new ResourceNotFoundException("ServiceZone not found with id: " + id);
        }
        return serviceZone;
    }

    public ServiceZone updateServiceZone(Long id, String name, String region, Double baseRate) {
        validateServiceZoneData(name, region, baseRate);
        ServiceZone serviceZone = getById(id);
        serviceZone.setName(name);
        serviceZone.setRegion(region);
        serviceZone.setBaseRate(baseRate);
        serviceZone.setUpdatedDate(new Date());
        return serviceZoneRepository.save(serviceZone);
    }

    public boolean deleteById(Long id) {
        ServiceZone serviceZone = getById(id);
        serviceZone.setActive(false);
        serviceZone.setUpdatedDate(new Date());
        serviceZoneRepository.save(serviceZone);
        return true;
    }
}
