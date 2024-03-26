package org.example.universitymanagementsystem.mapper;

import org.example.universitymanagementsystem.dto.*;
import org.example.universitymanagementsystem.repository.entity.DepartmentEntity;
import org.example.universitymanagementsystem.repository.vo.FindDepartmentsVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    List<DepartmentDTO> toDepartmentDTOList(List<DepartmentEntity> departmentEntities);

    DepartmentDTO toDepartmentDto(DepartmentEntity department);

    DepartmentDetailsDTO toDepartmentDetailsDTO(DepartmentEntity department);

    DepartmentEntity toDepartment(CreateDepartmentDTO createDepartmentDTO);

    void toDepartment(UpdateDepartmentDto updateDepartmentDto, @MappingTarget DepartmentEntity departmentEntity);

    @Mapping(target = "namePhrase", expression = "java(trimToNull(findDepartmentsDTO.getNamePhrase()))")
    @Mapping(target = "codePhrase", expression = "java(trimToNull(findDepartmentsDTO.getCodePhrase()))")
    FindDepartmentsVo toFindDepartmentsVo(FindDepartmentsDTO findDepartmentsDTO);

    default String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
