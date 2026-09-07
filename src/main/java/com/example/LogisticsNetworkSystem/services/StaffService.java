package com.example.LogisticsNetworkSystem.services;

import com.example.LogisticsNetworkSystem.entities.Staff;
import com.example.LogisticsNetworkSystem.entities.Warehouse;
import com.example.LogisticsNetworkSystem.exceptions.ResourceNotFoundException;
import com.example.LogisticsNetworkSystem.repositories.StaffRepository;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final WarehouseService warehouseService;

    public StaffService(
            StaffRepository staffRepository,
            WarehouseService warehouseService) {
        this.staffRepository = staffRepository;
        this.warehouseService = warehouseService;
    }

    private void validateStaffData(String name, String role, String phoneNumber) {
        if (name == null
                || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (name.length() > 255) {
            throw new IllegalArgumentException("Name cannot exceed 255 characters");
        }
        if (role == null
                || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Role cannot be blank");
        }
        if (role.length() > 255) {
            throw new IllegalArgumentException("Role cannot exceed 255 characters");
        }
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("PhoneNumber cannot be blank");
        }
        if (phoneNumber.length() > 255) {
            throw new IllegalArgumentException("PhoneNumber cannot exceed 255 characters");
        }
    }

    public Staff addStaff(Staff staff, Long warehouseId) {
        if (staff == null) {
            throw new IllegalArgumentException("Staff cannot be null");
        }
        validateStaffData(staff.getName(), staff.getRole(), staff.getPhoneNumber());
        Warehouse warehouse = warehouseService.getById(warehouseId);
        staff.setWarehouse(warehouse);
        staff.setActive(true);
        staff.setCreatedDate(new Date());
        return staffRepository.save(staff);
    }

    public List<Staff> getAllStaffs() {
        return staffRepository.findAll().stream().filter(Staff::isActive).toList();
    }

    public Staff getById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Staff ID must be greater than zero");
        }
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id: " + id));
        if (!staff.isActive()) {
            throw new ResourceNotFoundException("Staff not found with id: " + id);
        }
        return staff;
    }

    public Staff updateStaff(Long id, String name, String role, String phoneNumber, Long warehouseId) {
        validateStaffData(name, role, phoneNumber);
        Staff staff = getById(id);
        Warehouse warehouse = warehouseService.getById(warehouseId);
        staff.setName(name);
        staff.setRole(role);
        staff.setPhoneNumber(phoneNumber);
        staff.setWarehouse(warehouse);
        staff.setUpdatedDate(new Date());
        return staffRepository.save(staff);
    }

    public boolean deleteById(Long id) {
        Staff staff = getById(id);
        staff.setActive(false);
        staff.setUpdatedDate(new Date());
        staffRepository.save(staff);
        return true;
    }
}
