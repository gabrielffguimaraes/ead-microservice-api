package com.ead.course.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.OverridesAttribute;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="tb_modules")
public class Module implements Serializable {
    private final static long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID moduleId;

    private String title;
    private String description;
    private LocalDateTime creationDate;

    @JsonIgnoreProperties("modules")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="courseId",referencedColumnName = "courseId")
    private Course course;

    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnoreProperties("module")
    @OneToMany(mappedBy = "module",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Lesson> lessons;
}
