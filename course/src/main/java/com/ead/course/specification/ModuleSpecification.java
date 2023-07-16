package com.ead.course.specification;

import com.ead.course.models.Course;
import com.ead.course.models.Module;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.math.BigInteger;

public class ModuleSpecification {

    public static Specification<Module> filter(String title, BigInteger courseId) {
        return (root,criteriaQuery,criteriaBuilder) -> {
            var predicates = new ArrayList<>();
            if(title != null) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
            }

            if(courseId != null) {
                criteriaQuery.distinct(true);
                Root<Course> courseRoot = criteriaQuery.from(Course.class);
                Expression<Collection<Module>> modules = courseRoot.get("modules");

                predicates.add(criteriaBuilder.equal(courseRoot.get("courseId") , courseId));
                predicates.add(criteriaBuilder.isMember(root,modules));
            }

            return criteriaBuilder
                    .and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
