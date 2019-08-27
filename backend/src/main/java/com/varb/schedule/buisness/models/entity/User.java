package com.varb.schedule.buisness.models.entity;

import com.varb.schedule.buisness.models.business.RoleEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
public class User {

    @Id
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

}
