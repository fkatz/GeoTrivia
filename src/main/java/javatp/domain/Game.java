package javatp.domain;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "Games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    private Timestamp startTime;

    private Timestamp finishTime;

    private Integer hintsLeft;

    @ManyToOne
    @JoinColumn(name = "player_id")
    @JsonIgnore
    private User player;

    @ManyToOne(cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "root_waypoint_id")
    @JsonIgnore
    private Waypoint rootWaypoint;

    public Game() {
    }

    public Game(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

    @JsonIgnore
    public Waypoint getRootWaypoint() {
        return rootWaypoint;
    }

    @JsonIgnore
    public void setRootWaypoint(Waypoint rootWaypoint) {
        this.rootWaypoint = rootWaypoint;
    }

    public Integer getHintsLeft() {
        return hintsLeft;
    }

    public void setHintsLeft(Integer hintsLeft) {
        this.hintsLeft = hintsLeft;
    }


}