package org.example.universitymanagementsystem.repository;

import org.example.universitymanagementsystem.repository.entity.DepartmentEntity;
import org.example.universitymanagementsystem.repository.vo.FindDepartmentsVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

    Optional<DepartmentEntity> findByIdAndIsDeleted(Long id, boolean isDeleted);

    @Query("SELECT department FROM DepartmentEntity department " +
            " WHERE (:#{#findDepartmentsVo.namePhrase} IS NULL OR department.name LIKE %:#{#findDepartmentsVo.namePhrase}%)" +
            " AND (:#{#findDepartmentsVo.capacity} IS NULL OR department.capacity = :#{#findDepartmentsVo.capacity})" +
            " AND (:#{#findDepartmentsVo.codePhrase} IS NULL OR department.code LIKE %:#{#findDepartmentsVo.codePhrase}%)" +
            " AND (department.isDeleted = FALSE) ")
    Page<DepartmentEntity> findAll(FindDepartmentsVo findDepartmentsVo, PageRequest pageable);
}
