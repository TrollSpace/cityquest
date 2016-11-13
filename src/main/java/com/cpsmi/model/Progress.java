package com.cpsmi.model;

import javax.persistence.*;

/**
 * Created by Misha on 13.11.2016.
 */
@Entity
@Table(name = "progress")
public class Progress {

    public Progress() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
