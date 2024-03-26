package org.example.universitymanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.universitymanagementsystem.dto.CreateStudentDTO;
import org.example.universitymanagementsystem.dto.FindStudentsDTO;
import org.example.universitymanagementsystem.dto.StudentDTO;
import org.example.universitymanagementsystem.dto.UpdateStudentDTO;
import org.example.universitymanagementsystem.export.StudentExportHandler;
import org.example.universitymanagementsystem.manager.StudentManager;
import org.example.universitymanagementsystem.mapper.StudentMapper;
import org.example.universitymanagementsystem.repository.StudentRepository;
import org.example.universitymanagementsystem.shared.PageResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final StudentManager studentManager;
    private final StudentExportHandler studentExportHandler;

    public PageResponse<StudentDTO> findAll(FindStudentsDTO findStudentsDTO) {
        var studentEntities = studentManager.findAll(findStudentsDTO);
        var content = studentMapper.toStudentDTOList(studentEntities.getContent());
        return new PageResponse<>(content, studentEntities.getTotalPages(), studentEntities.getTotalElements());
    }

    public InputStreamResource export(FindStudentsDTO findStudentsDTO) {
        var studentEntities = studentManager.findAllToExport(findStudentsDTO);
        var content = studentMapper.toStudentDTOList(studentEntities);
        final String[] headers = {"ID", "Name", "Creation time", "Modification time"};

        var file = studentExportHandler.export(headers, content);

        return new InputStreamResource(file);
    }

    public StudentDTO findById(Long id) {
        return studentMapper.toStudentDTO(studentManager.getStudent(id));
    }

    public void create(CreateStudentDTO createStudentDTO) {
        studentRepository.save(studentMapper.toStudentEntity(createStudentDTO));
    }

    public void update(Long id, UpdateStudentDTO updateStudentDTO) {
        var student = studentManager.getStudent(id);
        studentMapper.toStudentEntity(updateStudentDTO, student);
        studentRepository.save(student);
    }

    public void delete(Long id) {
        var student = studentManager.getStudent(id);
        student.setDeleted(true);
        studentRepository.save(student);
    }
}
