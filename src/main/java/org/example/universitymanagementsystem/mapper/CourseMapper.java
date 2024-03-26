package org.example.universitymanagementsystem.mapper;

import org.example.universitymanagementsystem.dto.CourseDTO;
import org.example.universitymanagementsystem.dto.CreateCourseDTO;
import org.example.universitymanagementsystem.dto.FindCoursesDTO;
import org.example.universitymanagementsystem.dto.UpdateCourseDTO;
import org.example.universitymanagementsystem.repository.entity.CourseEntity;
import org.example.universitymanagementsystem.repository.vo.FindCoursesVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    List<CourseDTO> toCourseDTOList(List<CourseEntity> courseEntities);

    CourseEntity toCourseEntity(CreateCourseDTO createCourseDTO);

    CourseDTO toCourseDTO(CourseEntity courseEntity);

    void toCourseEntity(UpdateCourseDTO updateCourseDTO, @MappingTarget CourseEntity courseEntity);

    @Mapping(target = "namePhrase", expression = "java(trimToNull(findCoursesDTO.getNamePhrase()))")
    @Mapping(target = "codePhrase", expression = "java(trimToNull(findCoursesDTO.getCodePhrase()))")
    FindCoursesVo toFindCoursesVo(FindCoursesDTO findCoursesDTO);

    default String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
