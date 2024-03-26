package org.example.universitymanagementsystem.mapper;

import org.example.universitymanagementsystem.dto.*;
import org.example.universitymanagementsystem.repository.entity.InstructorEntity;
import org.example.universitymanagementsystem.repository.vo.FindInstructorsVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InstructorMapper {
    List<InstructorDTO> toInstructorDTOList(List<InstructorEntity> instructorEntities);

    InstructorDTO toInstructorDTO(InstructorEntity instructor);

    InstructorDetailsDTO toInstructorDetailsDTO(InstructorEntity instructor);

    InstructorEntity toInstructor(CreateInstructorDTO createInstructorDTO);

    void toInstructor(UpdateInstructorDTO updateInstructorDTO, @MappingTarget InstructorEntity instructor);

    @Mapping(target = "namePhrase", expression = "java(trimToNull(findInstructorsDTO.getNamePhrase()))")
    @Mapping(target = "emailPhrase", expression = "java(trimToNull(findInstructorsDTO.getEmailPhrase()))")
    @Mapping(target = "addressPhrase", expression = "java(trimToNull(findInstructorsDTO.getAddressPhrase()))")
    @Mapping(target = "phoneNumberPhrase", expression = "java(trimToNull(findInstructorsDTO.getPhoneNumberPhrase()))")
    @Mapping(target = "experiencePhrase", expression = "java(trimToNull(findInstructorsDTO.getExperiencePhrase()))")
    FindInstructorsVo toFindInstructorsVo(FindInstructorsDTO findInstructorsDTO);

    default String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

}
