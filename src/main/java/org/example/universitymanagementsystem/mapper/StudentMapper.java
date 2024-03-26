package org.example.universitymanagementsystem.mapper;

import org.example.universitymanagementsystem.dto.CreateStudentDTO;
import org.example.universitymanagementsystem.dto.FindStudentsDTO;
import org.example.universitymanagementsystem.dto.StudentDTO;
import org.example.universitymanagementsystem.dto.UpdateStudentDTO;
import org.example.universitymanagementsystem.repository.entity.StudentEntity;
import org.example.universitymanagementsystem.repository.vo.FindStudentsVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    List<StudentDTO> toStudentDTOList(List<StudentEntity> studentEntities);

    StudentEntity toStudentEntity(CreateStudentDTO createStudentDTO);

    StudentDTO toStudentDTO(StudentEntity studentEntity);

    void toStudentEntity(UpdateStudentDTO updateStudentDTO, @MappingTarget StudentEntity studentEntity);

    @Mapping(target = "namePhrase", expression = "java(trimToNull(findStudentsDTO.getNamePhrase()))")
    @Mapping(target = "emailPhrase", expression = "java(trimToNull(findStudentsDTO.getEmailPhrase()))")
    @Mapping(target = "addressPhrase", expression = "java(trimToNull(findStudentsDTO.getAddressPhrase()))")
    @Mapping(target = "phoneNumberPhrase", expression = "java(trimToNull(findStudentsDTO.getPhoneNumberPhrase()))")
    FindStudentsVo toFindStudentsVo(FindStudentsDTO findStudentsDTO);

    default String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
