package com.ead.authuser.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "tb_users_courses")
@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCourse implements Serializable {
    private static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private UUID courseId;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User userModel;

}
