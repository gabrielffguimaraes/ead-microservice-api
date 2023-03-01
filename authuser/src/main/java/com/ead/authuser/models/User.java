package com.ead.authuser.models;

import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

import static java.time.LocalDateTime.now;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="tb_users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger userId;
    @Column(unique = true,nullable = false,length = 50)
    private String username;
    @Column(unique = true,nullable = false,length = 50)
    private String email;
    @Column(nullable = false,length = 255)
    @JsonIgnore
    private String password;
    @Column(length = 255)
    @JsonIgnore
    private String oldPassword;
    @Column(length = 150)
    private String fullName;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(length = 20)
    private String phoneNumber;
    @Column(nullable = false,length = 20)
    private String cpf;
    @Column
    private String imageUrl;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastUpdateDate;

    @Column(length = 8)
    private String cep;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "userModel", fetch = FetchType.LAZY , cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Set<UserCourse> usersCourse;

    @PrePersist
    public void prePersist() {
        this.setCreationDate(now(ZoneId.of("America/Sao_Paulo")));
        this.setLastUpdateDate(now(ZoneId.of("America/Sao_Paulo")));
        this.userStatus = UserStatus.ACTIVE;
    }
    /*
    @Embedded
    UserAddress addressModel;*/

    /*
    @JsonIgnoreProperties("userModel")
    @OneToMany(mappedBy = "userModel")
    private List<UserAdressModel> adresses;*/
}

