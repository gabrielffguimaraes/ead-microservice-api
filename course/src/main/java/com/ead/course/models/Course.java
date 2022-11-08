package com.ead.course.models;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedEntityGraph(
        name = Course.MODULE_GRAPH,
        includeAllAttributes=true,
        attributeNodes = @NamedAttributeNode(value = "modules", subgraph = Module.LESSON_GRAPH),
        subgraphs = {
            @NamedSubgraph(
                name = Module.LESSON_GRAPH,
                attributeNodes = @NamedAttributeNode(value = "lessons")
            )
        }
)
@Table(name = "tb_courses")
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String MODULE_GRAPH = "Module.moduleId";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID courseId;
    private String name;
    private String description;
    private String imageUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime creationDate;
    private String lastUpdateDate;
    @Column
    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;
    @Column
    @Enumerated(EnumType.STRING)
    private CourseLevel courseLevel;
    private UUID userInstructor;

    @JsonIgnoreProperties("course")
    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<Module> modules;

}
