package com.ead.authuser.models;


import lombok.Data;

import javax.persistence.*;


@Data
@Embeddable
public class UserAddress {

    @Column(length = 8)
    private String cep;

    /*@JsonIgnoreProperties("adresses")
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    User userModel;*/
}
