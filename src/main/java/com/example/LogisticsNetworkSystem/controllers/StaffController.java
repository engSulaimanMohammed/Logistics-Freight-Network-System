package com.example.LogisticsNetworkSystem.controllers;

import com.example.LogisticsNetworkSystem.dtos.StaffDTO;
import com.example.LogisticsNetworkSystem.entities.Staff;
import com.example.LogisticsNetworkSystem.services.StaffService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/staff")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @PostMapping("/add")
    public StaffDTO add(@Valid @RequestBody StaffDTO dto) {
        Staff staff = new Staff();
        staff.setName(dto.getName());
        staff.setRole(dto.getRole());
        staff.setPhoneNumber(dto.getPhoneNumber());
        return StaffDTO.convertToDTO(staffService.addStaff(staff, dto.getWarehouseId()));
    }

    @GetMapping("/getAll")
    public List<StaffDTO> getAll() {
        return StaffDTO.convertToDTO(staffService.getAllStaffs());
    }

    @GetMapping("/getById/{id}")
    public StaffDTO getById(@PathVariable Long id) {
        return StaffDTO.convertToDTO(staffService.getById(id));
    }

    @PutMapping("/update/{id}")
    public StaffDTO update(@PathVariable Long id, @Valid @RequestBody StaffDTO dto) {
        return StaffDTO.convertToDTO(staffService.updateStaff(id, dto.getName(), dto.getRole(), dto.getPhoneNumber(), dto.getWarehouseId()));
    }

    @DeleteMapping("/delete/{id}")
    public StaffDTO delete(@PathVariable Long id) {
        StaffDTO dto = StaffDTO.convertToDTO(staffService.getById(id));
        staffService.deleteById(id);
        return dto;
    }
}
