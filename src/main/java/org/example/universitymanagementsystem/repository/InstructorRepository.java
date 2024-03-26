package org.example.universitymanagementsystem.repository;

import org.example.universitymanagementsystem.repository.entity.InstructorEntity;
import org.example.universitymanagementsystem.repository.vo.FindInstructorsVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<InstructorEntity, Long>, JpaSpecificationExecutor<InstructorEntity> {

    @Query("SELECT instructor  FROM InstructorEntity instructor" +
            " LEFT JOIN instructor.instructorProfileEntity instructorProfile" +
            " WHERE (:#{#findInstructorsVo.namePhrase} IS NULL OR instructor.name LIKE %:#{#findInstructorsVo.namePhrase}%)" +
            " AND (:#{#findInstructorsVo.emailPhrase} IS NULL OR instructorProfile.email LIKE %:#{#findInstructorsVo.emailPhrase}%)" +
            " AND (:#{#findInstructorsVo.addressPhrase} IS NULL OR instructorProfile.address LIKE %:#{#findInstructorsVo.addressPhrase}%)" +
            " AND (:#{#findInstructorsVo.phoneNumberPhrase} IS NULL OR instructorProfile.phoneNumber LIKE %:#{#findInstructorsVo.phoneNumberPhrase}%)" +
            " AND (:#{#findInstructorsVo.experiencePhrase} IS NULL OR instructor.experience LIKE %:#{#findInstructorsVo.experiencePhrase}%)" +
            " AND (instructor.isDeleted = FALSE) " +
            " ORDER BY instructor.id DESC")
    Page<InstructorEntity> findAll(FindInstructorsVo findInstructorsVo, Pageable pageable);

    Optional<InstructorEntity> findByIdAndIsDeleted(Long id, boolean isDeleted);
}
