package org.example.universitymanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.universitymanagementsystem.dto.CourseDTO;
import org.example.universitymanagementsystem.dto.CreateCourseDTO;
import org.example.universitymanagementsystem.dto.FindCoursesDTO;
import org.example.universitymanagementsystem.dto.UpdateCourseDTO;
import org.example.universitymanagementsystem.exception.CourseNotFoundException;
import org.example.universitymanagementsystem.export.CourseExportHandler;
import org.example.universitymanagementsystem.manager.CourseManager;
import org.example.universitymanagementsystem.manager.InstructorManager;
import org.example.universitymanagementsystem.mapper.CourseMapper;
import org.example.universitymanagementsystem.repository.CourseRepository;
import org.example.universitymanagementsystem.shared.PageResponse;
import org.example.universitymanagementsystem.validator.CourseValidator;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;
    private final CourseManager courseManager;
    private final CourseValidator courseValidator;
    private final InstructorManager instructorManager;
    private final CourseExportHandler courseExportHandler;

    public PageResponse<CourseDTO> findAll(FindCoursesDTO findCoursesDTO) {
        var courseEntities = courseManager.findAll(findCoursesDTO);
        var content = courseMapper.toCourseDTOList(courseEntities.getContent());
        return new PageResponse<>(content, courseEntities.getTotalPages(), courseEntities.getTotalElements());
    }

    public InputStreamResource export(FindCoursesDTO findCoursesDTO) {
        var courseEntities = courseManager.findAllToExport(findCoursesDTO);
        var content = courseMapper.toCourseDTOList(courseEntities);
        final String[] headers = {"ID", "Name", "Capacity", "Code", "Credit", "Creation time", "Modification time"};

        var file = courseExportHandler.export(headers, content);

        return new InputStreamResource(file);
    }

    public CourseDTO findById(@PathVariable Long id) {
        var course = courseManager.findByIdAndIsDeleted(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found : " + id));
        return courseMapper.toCourseDTO(course);
    }

    public void create(CreateCourseDTO createCourseDTO) {
        courseRepository.save(courseMapper.toCourseEntity(createCourseDTO));
    }

    public void update(Long id, UpdateCourseDTO updateCourseDTO) {
        var course = courseManager.getCourse(id);
        courseMapper.toCourseEntity(updateCourseDTO, course);
        courseRepository.save(course);
    }

    public void delete(Long id) {
        var course = courseManager.getCourse(id);
        course.setDeleted(true);
        courseRepository.save(course);
    }

    public void updateCapacity(Long id, long capacity) {
        var course = courseManager.getCourse(id);
        if (course.getCapacity() != capacity) {
            course.setCapacity(capacity);
            courseValidator.validate(course);
            courseRepository.save(course);
        }
    }

    public void updateInstructor(Long instructorId, Long courseId) {
        var course = courseManager.getCourse(courseId);
        if (instructorId == null) {
            course.setInstructor(null);
            courseRepository.save(course);
            return;
        }
        var instructor = instructorManager.getInstructor(instructorId);
        course.setInstructor(instructor);
        courseRepository.save(course);
    }
}
