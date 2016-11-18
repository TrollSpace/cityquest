package com.cpsmi.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Misha on 13.11.2016.
 */
@Entity
@Table(name = "user")
public class User {

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "birthDate")
    private Date birthDate;

    @Column(name = "language")
    private String language;

    @Column(name = "Name")
    private String Name;

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
