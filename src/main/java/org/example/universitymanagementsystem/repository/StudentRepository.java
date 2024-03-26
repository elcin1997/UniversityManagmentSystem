package org.example.universitymanagementsystem.repository;

import org.example.universitymanagementsystem.repository.entity.StudentEntity;
import org.example.universitymanagementsystem.repository.vo.FindStudentsVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long>, JpaSpecificationExecutor<StudentEntity> {

    Optional<StudentEntity> findByIdAndIsDeleted(Long id, boolean isDeleted);

    @Query("SELECT student  FROM StudentEntity student" +
            " LEFT JOIN student.studentProfileEntity studentProfile" +
            " LEFT JOIN student.courses course" +
            " WHERE (:#{#findStudentsVo.namePhrase} IS NULL OR student.name LIKE %:#{#findStudentsVo.namePhrase}%)" +
            " AND (:#{#findStudentsVo.emailPhrase} IS NULL OR studentProfile.email LIKE %:#{#findStudentsVo.emailPhrase}%)" +
            " AND (:#{#findStudentsVo.addressPhrase} IS NULL OR studentProfile.address LIKE %:#{#findStudentsVo.addressPhrase}%)" +
            " AND (:#{#findStudentsVo.phoneNumberPhrase} IS NULL OR studentProfile.phoneNumber LIKE %:#{#findStudentsVo.phoneNumberPhrase}%)" +
            " AND (:#{#findStudentsVo.courseId} IS NULL OR course.id = :#{#findStudentsVo.courseId})" +
            " AND (student.isDeleted = FALSE) AND (course.id IS NULL OR course.isDeleted = FALSE) " +
            " ORDER BY student.id DESC")
    Page<StudentEntity> findAll(FindStudentsVo findStudentsVo, PageRequest pageable);

}

