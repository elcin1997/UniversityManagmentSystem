package org.example.universitymanagementsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.universitymanagementsystem.dto.*;
import org.example.universitymanagementsystem.service.DepartmentService;
import org.example.universitymanagementsystem.shared.PageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<PageResponse<DepartmentDTO>> findAll(FindDepartmentsDTO findDepartmentsDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.findAll(findDepartmentsDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('DEPARTMENT')")
    public ResponseEntity<DepartmentDetailsDTO> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid CreateDepartmentDTO createDepartmentDTO) {
        departmentService.create(createDepartmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid UpdateDepartmentDto updateDepartmentDto) {
        departmentService.update(id, updateDepartmentDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        departmentService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
