package com.cpsmi.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Misha on 13.11.2016.
 */
@Entity
@Table(name = "progress",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "point_in_quest_id"}))
public class Progress implements Serializable {

    public Progress() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_in_quest_id")
    private PointInQuest pointInQuest;

    @Column(name = "last_used_hint_id", nullable = false, columnDefinition = "int default 0")
    private int lastUsedHintId;

    @Column
    private Date start;

    @Column
    private Date end;

    @Column(name = "end_latitude")
    private double endLatitude;

    @Column(name = "end_longitude")
    private double endLongitude;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PointInQuest getPointInQuest() {
        return pointInQuest;
    }

    public void setPointInQuest(PointInQuest pointInQuest) {
        this.pointInQuest = pointInQuest;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(double endLatitude) {
        this.endLatitude = endLatitude;
    }

    public double getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(double endLongitude) {
        this.endLongitude = endLongitude;
    }

    public int getLastUsedHintId() {
        return lastUsedHintId;
    }

    public void setLastUsedHintId(int lastUsedHintId) {
        this.lastUsedHintId = lastUsedHintId;
    }
}
