package com.ead.course.specification;

import com.ead.course.models.Course;
import com.ead.course.models.Module;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class ModuleSpecification {
    @Contract(pure = true)
    public static @NotNull Specification<Module> filter(String title, UUID courseId) {
        return (root,criteriaQuery,criteriaBuilder) -> {
            var predicates = new ArrayList<>();
            if(title != null) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
            }

            if(courseId != null) {
                criteriaQuery.distinct(true);
                var courseRoot = criteriaQuery.from(Course.class);
                Expression<Collection<Module>> modules = courseRoot.get("modules");

                predicates.add(criteriaBuilder.equal(courseRoot.get("courseId") , courseId));
                predicates.add(criteriaBuilder.isMember(root,modules));
            }

            return criteriaBuilder
                    .and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
