package org.example.universitymanagementsystem.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.example.universitymanagementsystem.repository.entity.InstructorEntity;
import org.example.universitymanagementsystem.repository.entity.InstructorEntity_;
import org.example.universitymanagementsystem.repository.entity.InstructorProfileEntity_;
import org.example.universitymanagementsystem.repository.vo.FindInstructorsVo;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class InstructorSearchSpecification implements Specification<InstructorEntity> {
    private final FindInstructorsVo findInstructorsVo;

    @Override
    public Predicate toPredicate(Root<InstructorEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return Specification.where(nameLike())
                .and(emailLike())
                .and(phoneNumberLike())
                .and(experienceLike())
                .and(addressLike())
                .and(isNotDeleted())
                .toPredicate(root, query, criteriaBuilder);
    }

    public Specification<InstructorEntity> nameLike() {
        var namePhrase = findInstructorsVo.getNamePhrase();
        if (namePhrase == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.get(InstructorEntity_.name),
                        '%' + namePhrase + '%', '\\');
    }

    public Specification<InstructorEntity> emailLike() {
        var emailPhrase = findInstructorsVo.getEmailPhrase();
        if (emailPhrase == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.join(InstructorEntity_.instructorProfileEntity).get(InstructorProfileEntity_.email),
                        '%' + emailPhrase + '%', '\\');
    }

    public Specification<InstructorEntity> addressLike() {
        var addressPhrase = findInstructorsVo.getAddressPhrase();
        if (addressPhrase == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.lower(root.join(InstructorEntity_.instructorProfileEntity).get(InstructorProfileEntity_.address)),
                        '%' + addressPhrase.toLowerCase() + '%', '\\');
    }

    public Specification<InstructorEntity> phoneNumberLike() {
        var phoneNumberPhrase = findInstructorsVo.getPhoneNumberPhrase();
        if (phoneNumberPhrase == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.join(InstructorEntity_.instructorProfileEntity).get(InstructorProfileEntity_.phoneNumber),
                        '%' + phoneNumberPhrase + '%', '\\');
    }

    public Specification<InstructorEntity> experienceLike() {
        var experiencePhrase = findInstructorsVo.getExperiencePhrase();
        if (experiencePhrase == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.join(InstructorEntity_.instructorProfileEntity).get(InstructorProfileEntity_.phoneNumber),
                        '%' + experiencePhrase + '%', '\\');
    }

    public Specification<InstructorEntity> isNotDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get(InstructorEntity_.isDeleted), false);
    }
}
