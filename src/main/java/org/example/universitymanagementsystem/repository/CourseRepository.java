package org.example.universitymanagementsystem.repository;

import org.example.universitymanagementsystem.repository.entity.CourseEntity;
import org.example.universitymanagementsystem.repository.vo.FindCoursesVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long>, JpaSpecificationExecutor<CourseEntity> {

    @Query("SELECT course FROM CourseEntity course " +
            " LEFT JOIN course.students student " +
            " WHERE (:#{#findCoursesVo.studentId} IS NULL OR student.id = :#{#findCoursesVo.studentId})" +
            " AND (:#{#findCoursesVo.namePhrase} IS NULL OR course.name LIKE %:#{#findCoursesVo.namePhrase}%)" +
            " AND (:#{#findCoursesVo.capacity} IS NULL OR course.capacity = :#{#findCoursesVo.capacity})" +
            " AND (:#{#findCoursesVo.credit} IS NULL OR course.credit = :#{#findCoursesVo.credit})" +
            " AND (:#{#findCoursesVo.codePhrase} IS NULL OR course.code LIKE %:#{#findCoursesVo.codePhrase}%)" +
            " AND (course.isDeleted = FALSE) AND (student.id IS NULL OR student.isDeleted = FALSE)" +
            " ORDER BY course.id")
    Page<CourseEntity> findAll(FindCoursesVo findCoursesVo, Pageable pageable);

    Optional<CourseEntity> findByIdAndIsDeleted(Long id, boolean isDeleted);
}
