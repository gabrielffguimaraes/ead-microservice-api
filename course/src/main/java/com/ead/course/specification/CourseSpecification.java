package com.ead.course.specification;


import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.ead.course.models.Course;
import com.ead.course.models.CourseSchedule;
import com.ead.course.models.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Slf4j
public class CourseSpecification {
    public static Specification<Course> filter(CourseLevel courseLevel, CourseStatus courseStatus, String name, BigInteger userId) {
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
          if(userId != null) {
              predicates.add(criteriaBuilder.equal(root.join("courseUsers").get("userId"),userId));
          }
          return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
    public static Specification<UserModel> filterUserId(UUID courseId) {

        return (root,query,builder) -> {
            var predicates = new ArrayList<>();
            if(courseId != null) {
                query.distinct(true);



                Root<Course> courseRoot = query.from(Course.class);
                Expression<Collection<UserModel>> users = courseRoot.get("users");



                predicates.add(builder.equal(courseRoot.get("courseId") , courseId));


                predicates.add(builder.isMember(root,users));

            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    /*
    public static Specification<Course> courseUserId(UUID userId) {
        var predicates = new ArrayList<>();
        return (root,query,builder) -> {
            query.distinct(true);
            if(userId != null) {
                Root<UserModel> rootUserModel = query.from(UserModel.class);
                predicates.add(builder.equal(rootUserModel.get("id"),userId));

                Expression<Collection<Course>> courses = rootUserModel.get("courses");
                predicates.add(builder.isMember(root,courses));
            }
            return builder.and();
        };
    }*/























    public static Specification<Course> filterCoursesByUser(UUID userId) {
        return (root, query,builder) -> {
            query.distinct(true);
            List<Predicate> predicates = new ArrayList<>();
            /*
            Root<UserModel> usersRoot = query.from(UserModel.class);*/
            log.info("USER ID === {}",userId);
            predicates.add(builder.equal(root.join("users").get("userId"),userId));
/*
            Expression<Collection<Course>> courses = usersRoot.get("courses");
            predicates.add(builder.isMember(root,courses));*/

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
