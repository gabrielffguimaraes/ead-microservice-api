package com.ead.authuser.specification;

import com.ead.authuser.filters.UserFilter;
import com.ead.authuser.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserSpec {
    public static Specification<User> filter(UserFilter params) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(params.getCpf() != null) {
                predicates.add(criteriaBuilder.equal(root.get("cpf"), params.getCpf()));
            }
            if(params.getFullName() != null) {
                predicates.add(criteriaBuilder.like(root.get("fullName"),"%"+params.getFullName()+"%"));
            }
            if(params.getEmail() != null) {
                predicates.add(criteriaBuilder.like(root.get("email"),"%"+params.getEmail()+"%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
