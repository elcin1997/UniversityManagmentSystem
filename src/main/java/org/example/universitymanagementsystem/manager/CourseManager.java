package org.example.universitymanagementsystem.manager;

import lombok.RequiredArgsConstructor;
import org.example.universitymanagementsystem.annotation.Manager;
import org.example.universitymanagementsystem.dto.FindCoursesDTO;
import org.example.universitymanagementsystem.exception.CourseNotFoundException;
import org.example.universitymanagementsystem.mapper.CourseMapper;
import org.example.universitymanagementsystem.repository.CourseRepository;
import org.example.universitymanagementsystem.repository.entity.CourseEntity;
import org.example.universitymanagementsystem.specification.CourseSearchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@Manager
@RequiredArgsConstructor
public class CourseManager {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseEntity getCourse(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course Not Found: " + id));
    }

    public Optional<CourseEntity> findByIdAndIsDeleted(Long id) {
        return courseRepository.findByIdAndIsDeleted(id, false);
    }

    public Page<CourseEntity> findAll(FindCoursesDTO findCoursesDTO) {
        var pageable = PageRequest.of(findCoursesDTO.getPage(), findCoursesDTO.getSize(), Sort.by("id").ascending());
        var findCoursesVo = courseMapper.toFindCoursesVo(findCoursesDTO);
        var courseSearchSpecification = new CourseSearchSpecification(findCoursesVo);
        return courseRepository.findAll(courseSearchSpecification, pageable);
    }

    public List<CourseEntity> findAllToExport(FindCoursesDTO findCoursesDTO) {
        var findCoursesVo = courseMapper.toFindCoursesVo(findCoursesDTO);
        var courseSearchSpecification = new CourseSearchSpecification(findCoursesVo);
        return courseRepository.findAll(courseSearchSpecification);
    }
}
