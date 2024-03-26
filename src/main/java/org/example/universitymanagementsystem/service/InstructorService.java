package org.example.universitymanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.universitymanagementsystem.dto.*;
import org.example.universitymanagementsystem.export.InstructorExportHandler;
import org.example.universitymanagementsystem.manager.InstructorManager;
import org.example.universitymanagementsystem.mapper.InstructorMapper;
import org.example.universitymanagementsystem.repository.InstructorRepository;
import org.example.universitymanagementsystem.shared.PageResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final InstructorMapper instructorMapper;
    private final InstructorManager instructorManager;
    private final InstructorExportHandler instructorExportHandler;

    public PageResponse<InstructorDTO> findAll(FindInstructorsDTO findInstructorsDTO) {
        var instructorEntities = instructorManager.findAll(findInstructorsDTO);
        var content = instructorMapper.toInstructorDTOList(instructorEntities.getContent());
        return new PageResponse<>(content, instructorEntities.getTotalPages(), instructorEntities.getTotalElements());
    }

    public InputStreamResource export(FindInstructorsDTO findInstructorsDTO) {
        var instructorEntities = instructorManager.findAllToExport(findInstructorsDTO);
        var content = instructorMapper.toInstructorDTOList(instructorEntities);
        final String[] headers = {"ID", "Name", "Creation time", "Modification time"};
        var file = instructorExportHandler.export(headers, content);
        return new InputStreamResource(file);
    }

    public InstructorDetailsDTO findById(Long id) {
        return instructorMapper.toInstructorDetailsDTO(instructorManager.getInstructor(id));
    }

    public void create(CreateInstructorDTO createInstructorDTO) {
        var instructor = instructorMapper.toInstructor(createInstructorDTO);
        instructorRepository.save(instructor);
    }

    public void update(Long id, UpdateInstructorDTO updateInstructorDTO) {
        var instructor = instructorManager.getInstructor(id);
        instructorMapper.toInstructor(updateInstructorDTO, instructor);
        instructorRepository.save(instructor);
    }

    public void delete(Long id) {
        var instructor = instructorManager.getInstructor(id);
        instructor.setDeleted(true);
        instructorRepository.save(instructor);
    }
}
