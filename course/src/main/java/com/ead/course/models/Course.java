package com.ead.course.models;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedEntityGraph(
        includeAllAttributes=true,
        attributeNodes = {
                @NamedAttributeNode(value = "modules", subgraph = "Lesson.lessons"),
        },
        subgraphs = {
            @NamedSubgraph(
                name = "Lesson.lessons",
                attributeNodes = @NamedAttributeNode(value = "lessons")
            )
        }
)
@Table(name = "tb_courses")
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String LESSON_GRAPH = "Lesson.modules";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID courseId;
    private String name;
    private String description;
    private String imageUrl;
    //@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime creationDate;
    //@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime lastUpdateDate;
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
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Module> modules;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "tb_course_schedule",
            joinColumns = @JoinColumn(name = "courseId"),
            inverseJoinColumns = @JoinColumn(name = "scheduleId")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Schedule> schedule;

    @PrePersist
    public void prePersist() {
        this.setCreationDate(LocalDateTime.now());
        this.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
    }
}
