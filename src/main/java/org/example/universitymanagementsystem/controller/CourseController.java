package org.example.universitymanagementsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.universitymanagementsystem.dto.CourseDTO;
import org.example.universitymanagementsystem.dto.CreateCourseDTO;
import org.example.universitymanagementsystem.dto.FindCoursesDTO;
import org.example.universitymanagementsystem.dto.UpdateCourseDTO;
import org.example.universitymanagementsystem.service.CourseService;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<PageResponse<CourseDTO>> findAll(FindCoursesDTO findCoursesDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll(findCoursesDTO));
    }

    @GetMapping("/export")
    @PreAuthorize("hasAnyAuthority('DEPARTMENT')")
    public ResponseEntity<Resource> export(FindCoursesDTO findCoursesDTO) {
        InputStreamResource inputStreamResource = courseService.export(findCoursesDTO);
        var filename = "University Management System Courses - " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()) + ".xlsx";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(inputStreamResource);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('DEPARTMENT')")
    public ResponseEntity<CourseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('DEPARTMENT')")
    public ResponseEntity<Void> create(@RequestBody CreateCourseDTO createCourseDTO) {
        courseService.create(createCourseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('DEPARTMENT')")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid UpdateCourseDTO updateCourseDTO) {
        courseService.update(id, updateCourseDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}/capacity")
    @PreAuthorize("hasAnyAuthority('DEPARTMENT')")
    public ResponseEntity<Void> updateCapacity(@PathVariable Long id, @RequestParam long capacity) {
        courseService.updateCapacity(id, capacity);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}/instructor")
    @PreAuthorize("hasAnyAuthority('DEPARTMENT')")
    public ResponseEntity<Void> updateInstructor(@PathVariable Long id, @RequestParam(required = false) Long instructorId) {
        courseService.updateInstructor(instructorId, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('DEPARTMENT')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
