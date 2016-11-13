package com.cpsmi.model;

import javax.persistence.*;

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

    /*@OneToMany(mappedBy = "user")
    Progress progress;
*/
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
