package org.example.universitymanagementsystem.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.example.universitymanagementsystem.repository.entity.CourseEntity;
import org.example.universitymanagementsystem.repository.entity.CourseEntity_;
import org.example.universitymanagementsystem.repository.entity.StudentEntity_;
import org.example.universitymanagementsystem.repository.vo.FindCoursesVo;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class CourseSearchSpecification implements Specification<CourseEntity> {
    private final FindCoursesVo findCoursesVo;

    @Override
    public Predicate toPredicate(Root<CourseEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        return Specification.where(nameLike()).and(studentId()).and(codePhraseLike()).and(credit()).and(capacity()).and(isNotDeleted()).toPredicate(root, query, criteriaBuilder);
    }

    public Specification<CourseEntity> studentId() {
        var studentId = findCoursesVo.getStudentId();
        if (studentId == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(CourseEntity_.students).get(StudentEntity_.id), studentId);
    }

    public Specification<CourseEntity> nameLike() {
        var namePhrase = findCoursesVo.getNamePhrase();
        if (namePhrase == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(CourseEntity_.name), '%' + namePhrase + '%', '\\');
    }

    public Specification<CourseEntity> capacity() {
        var capacity = findCoursesVo.getCapacity();
        if (capacity == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(CourseEntity_.capacity), capacity);
    }

    public Specification<CourseEntity> credit() {
        var credit = findCoursesVo.getCredit();
        if (credit == null) {
            return null;
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(CourseEntity_.credit), credit);
    }

    public Specification<CourseEntity> codePhraseLike() {
        var codePhrase = findCoursesVo.getCodePhrase();
        if (codePhrase == null) {
            return null;
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(CourseEntity_.code), '%' + codePhrase + '%', '\\');
    }

    public Specification<CourseEntity> isNotDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(CourseEntity_.isDeleted), false);
    }
}
