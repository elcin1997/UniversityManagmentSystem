package org.example.universitymanagementsystem.manager;

import lombok.RequiredArgsConstructor;
import org.example.universitymanagementsystem.annotation.Manager;
import org.example.universitymanagementsystem.dto.FindDepartmentsDTO;
import org.example.universitymanagementsystem.exception.DepartmentNotFoundException;
import org.example.universitymanagementsystem.mapper.DepartmentMapper;
import org.example.universitymanagementsystem.repository.DepartmentRepository;
import org.example.universitymanagementsystem.repository.entity.DepartmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Manager
@RequiredArgsConstructor
public class DepartmentManager {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentEntity getDepartment(Long id) {
        return departmentRepository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with id:" + id));
    }

    public Page<DepartmentEntity> findAll(FindDepartmentsDTO findDepartmentsDTO) {
        var sort = Sort.by("id").ascending();
        var pageable = PageRequest.of(findDepartmentsDTO.getPage(), findDepartmentsDTO.getSize(), sort);
        var findDepartmentsVo = departmentMapper.toFindDepartmentsVo(findDepartmentsDTO);
        return departmentRepository.findAll(findDepartmentsVo, pageable);
    }
}
