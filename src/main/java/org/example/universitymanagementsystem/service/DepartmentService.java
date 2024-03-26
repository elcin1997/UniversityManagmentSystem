package org.example.universitymanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.universitymanagementsystem.dto.*;
import org.example.universitymanagementsystem.manager.DepartmentManager;
import org.example.universitymanagementsystem.mapper.DepartmentMapper;
import org.example.universitymanagementsystem.repository.DepartmentRepository;
import org.example.universitymanagementsystem.shared.PageResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final DepartmentManager departmentManager;

    public PageResponse<DepartmentDTO> findAll(FindDepartmentsDTO findDepartmentsDTO) {
        var departmentEntities = departmentManager.findAll(findDepartmentsDTO);
        var content = departmentMapper.toDepartmentDTOList(departmentEntities.getContent());
        return new PageResponse<>(content, departmentEntities.getTotalPages(), departmentEntities.getTotalElements());
    }

    public DepartmentDetailsDTO findById(Long id) {
        return departmentMapper.toDepartmentDetailsDTO(departmentManager.getDepartment(id));
    }

    public void create(CreateDepartmentDTO createDepartmentDTO) {
        var department = departmentMapper.toDepartment(createDepartmentDTO);
        departmentRepository.save(department);
    }

    public void update(Long id, UpdateDepartmentDto updateDepartmentDto) {
        var department = departmentManager.getDepartment(id);
        departmentMapper.toDepartment(updateDepartmentDto, department);
        departmentRepository.save(department);
    }

    public void delete(Long id) {
        var department = departmentManager.getDepartment(id);
        department.setDeleted(true);
        departmentRepository.save(department);
    }
}
