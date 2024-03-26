package org.example.universitymanagementsystem.specification;

import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.example.universitymanagementsystem.repository.entity.*;
import org.example.universitymanagementsystem.repository.vo.FindStudentsVo;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public final class StudentSearchSpecification implements Specification<StudentEntity> {
    private final FindStudentsVo findStudentsVo;

    @Override
    public Predicate toPredicate(Root<StudentEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return Specification.where(nameLike())
                .and(emailLike())
                .and(phoneNumberLike())
                .and(addressLike())
                .and(enrolledToCourse())
                .and(isNotDeleted())
                .and(courseIsNotDeleted())
                .toPredicate(root, query, criteriaBuilder);
    }

    public Specification<StudentEntity> nameLike() {
        var namePhrase = findStudentsVo.getNamePhrase();
        if (namePhrase == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.get(StudentEntity_.name),
                        '%' + namePhrase + '%', '\\');
    }

    public Specification<StudentEntity> emailLike() {
        var emailPhrase = findStudentsVo.getEmailPhrase();
        if (emailPhrase == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.join(StudentEntity_.studentProfileEntity).get(StudentProfileEntity_.email),
                        '%' + emailPhrase + '%', '\\');
    }

    public Specification<StudentEntity> addressLike() {
        var addressPhrase = findStudentsVo.getAddressPhrase();
        if (addressPhrase == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.lower(root.join(StudentEntity_.studentProfileEntity).get(StudentProfileEntity_.address)),
                        '%' + addressPhrase.toLowerCase() + '%', '\\');
    }

    public Specification<StudentEntity> phoneNumberLike() {
        var phoneNumberPhrase = findStudentsVo.getPhoneNumberPhrase();
        if (phoneNumberPhrase == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.join(StudentEntity_.studentProfileEntity).get(StudentProfileEntity_.phoneNumber),
                        '%' + phoneNumberPhrase + '%', '\\');
    }

    public Specification<StudentEntity> enrolledToCourse() {
        var courseId = findStudentsVo.getCourseId();
        if (courseId == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.join(StudentEntity_.courses).get(CourseEntity_.id), courseId);
    }

    public Specification<StudentEntity> isNotDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get(StudentEntity_.isDeleted), false);
    }

    public Specification<StudentEntity> courseIsNotDeleted() {
        return (root, query, criteriaBuilder) -> {
            Join<StudentEntity, CourseEntity> courseJoin = root.join("courses", JoinType.LEFT);
            Predicate courseIsNull = criteriaBuilder.isNull(courseJoin.get(CourseEntity_.id));
            Predicate courseNotDeleted = criteriaBuilder.isFalse(courseJoin.get(CourseEntity_.isDeleted));
            return criteriaBuilder.or(courseIsNull, courseNotDeleted);
        };
    }


}
