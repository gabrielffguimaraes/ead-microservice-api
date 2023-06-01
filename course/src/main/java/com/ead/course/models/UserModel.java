package com.ead.course.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "TB_USERS")
@Entity
public class UserModel {
    @Id
    private UUID id;

    @Id
    private UUID userId;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(nullable = false, length = 150)
    private String fullName;
    @Column(nullable=false)
    private String userStatus;
    @Column(nullable=false)
    private String userType;
    @Column(length = 20)
    private String cpf;
    @Column
    private String imageUrl;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    public List<Course> courses;

}



