package org.example.universitymanagementsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.universitymanagementsystem.dto.*;
import org.example.universitymanagementsystem.service.InstructorService;
import org.example.universitymanagementsystem.shared.PageResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v1/instructors")
@RequiredArgsConstructor
public class InstructorController {
    private final InstructorService instructorService;

    @GetMapping
    public ResponseEntity<PageResponse<InstructorDTO>> findAll(FindInstructorsDTO findInstructorsDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(instructorService.findAll(findInstructorsDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR', 'DEPARTMENT')")
    public ResponseEntity<InstructorDetailsDTO> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(instructorService.findById(id));
    }

    @GetMapping("/export")
    @PreAuthorize("hasAnyAuthority('DEPARTMENT')")
    public ResponseEntity<Resource> export(FindInstructorsDTO findInstructorsDTO) {
        InputStreamResource inputStreamResource = instructorService.export(findInstructorsDTO);
        var filename = "University Management System Instructors - " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()) + ".xlsx";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(inputStreamResource);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid CreateInstructorDTO createInstructorDTO) {
        instructorService.create(createInstructorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid UpdateInstructorDTO updateInstructorDTO) {
        instructorService.update(id, updateInstructorDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        instructorService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
