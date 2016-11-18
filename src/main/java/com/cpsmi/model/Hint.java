package com.cpsmi.model;

import javax.persistence.*;

/**
 * Created by Misha on 13.11.2016.
 */
@Entity
@Table(name = "hint")
public class Hint {

    public Hint() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "hint_order")
    private int hintOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    private Point point;

    @Column(name = "hint_text")
    private String hintText;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public int getHintOrder() {
        return hintOrder;
    }

    public void setHintOrder(int hintOrder) {
        this.hintOrder = hintOrder;
    }

    public String getHintText() {
        return hintText;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
    }
}
