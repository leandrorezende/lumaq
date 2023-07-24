package com.fortbit.lumaq.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_AUTH")
public class UserAuth {

    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "EMAIL_ID")
    private String emailId;
    @Column(name = "NAME")
    private String name;
    @Column(name = "PASSWORD")
    private String password;

    public String getId() {
        return id;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

}
