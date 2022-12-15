package com.ead.course.specification;


import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.ead.course.models.Course;
import org.hibernate.usertype.UserType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class CourseSpecification {
    public static Specification<Course> filter(CourseLevel courseLevel, CourseStatus courseStatus,String name) {
        return (root,query,criteriaBuilder) -> {
          var predicates = new ArrayList<>();
          if(courseLevel != null) {
              predicates.add(criteriaBuilder.equal(root.get("courseLevel"), courseLevel));
          }
          if(courseStatus != null) {
              predicates.add(criteriaBuilder.equal(root.get("courseStatus"), courseStatus));
          }
          if(name != null) {
              predicates.add(criteriaBuilder.like(root.get("name"),"%"+name+"%"));
          }
          return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
