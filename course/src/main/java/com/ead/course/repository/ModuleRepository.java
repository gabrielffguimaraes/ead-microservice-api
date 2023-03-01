package com.ead.course.repository;
import com.ead.course.models.Lesson;
import com.ead.course.models.Module;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.math.BigInteger;

public interface ModuleRepository extends JpaRepository<Module, BigInteger> , JpaSpecificationExecutor<Module> {

}
