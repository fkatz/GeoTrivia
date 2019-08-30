package javatp.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Waypoints")
public class Waypoint {

    public Waypoint() {
    }

    public Waypoint(boolean isCorrect) {
        this.isCorrrect = isCorrect;
    }

    public Waypoint(boolean isCorrect, List<Waypoint> next) {
        for (Waypoint waypoint : next) {
            waypoint.setPrev(this);
        }
        this.isCorrrect = isCorrect;
        this.next = next;
    }

    public Waypoint(Long id) {
        this.id = id;
    }

    private Boolean isCorrrect;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer selectedAnswer;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "prev_id")
    @JsonIgnore
    private Waypoint prev;

    @OneToMany(mappedBy = "prev", cascade = { CascadeType.PERSIST })
    private List<Waypoint> next;

    @ManyToOne
    @JoinColumn(name = "poi_id")
    private POI poi;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToMany
    @JoinTable(name = "waypoint_hints", joinColumns = @JoinColumn(name = "waypoint_id"), inverseJoinColumns = @JoinColumn(name = "hint_id"))
    List<Hint> givenHints;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Waypoint getPrev() {
        return prev;
    }

    @JsonIgnore
    public void setPrev(Waypoint prev) {
        this.prev = prev;
    }

    public POI getPOI() {
        return poi;
    }

    public void setPOI(POI poi) {
        this.poi = poi;
    }

    public Boolean getIsCorrrect() {
        return isCorrrect;
    }

    public void setIsCorrrect(Boolean isCorrrect) {
        this.isCorrrect = isCorrrect;
    }

    public List<Waypoint> getNext() {
        return next;
    }

    public void setNext(List<Waypoint> next) {
        for (Waypoint waypoint : next) {
            waypoint.setPrev(this);
        }
        this.next = next;
    }

    @JsonIgnore
    private WaypointDepth getLastCorrectWaypointDepth() {
        Waypoint lastCorrect = this;
        int depth = 0;
        while (lastCorrect.getNext().size() != 0) {
            for (Waypoint child : lastCorrect.getNext()) {
                if (child.getIsCorrrect()) {
                    lastCorrect = child;
                    depth++;
                    break;
                }
            }
        }
        return new WaypointDepth(lastCorrect, depth);
    }

    @JsonIgnore
    public Waypoint getLastUnresolved() {
        Waypoint lastCorrect = getLastCorrectWaypointDepth().waypoint;
        return lastCorrect.prev == null ? lastCorrect : lastCorrect.getPrev();
    }

    @JsonIgnore
    public Waypoint getLastCorrect() {
        return getLastCorrectWaypointDepth().waypoint;
    }

    @JsonIgnore
    public int getCurrentDepth() {
        return getLastCorrectWaypointDepth().depth;
    }

    @JsonIgnore
    public List<POI> getUsedPOIs() {
        ArrayList<POI> usedPOIs = new ArrayList<POI>();
        Waypoint lastCorrect = this;
        while (lastCorrect.getNext().size() != 0) {
            for (Waypoint child : lastCorrect.getNext()) {
                if (child.getIsCorrrect()) {
                    lastCorrect = child;
                    usedPOIs.add(child.getPOI());
                    break;
                }
            }
        }
        return usedPOIs;
    }

    @JsonIgnore
    public Question getQuestion() {
        return question;
    }

    @JsonIgnore
    public void setQuestion(Question question) {
        this.question = question;
    }

    @JsonIgnore
    public List<Hint> getGivenHints() {
        return givenHints;
    }

    @JsonIgnore
    public void setGivenHints(List<Hint> givenHints) {
        this.givenHints = givenHints;
    }

    @JsonIgnore
    public Answer getSelectedAnswer() {
        return selectedAnswer;
    }

    @JsonIgnore
    public void setSelectedAnswer(Answer selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    private class WaypointDepth {
        public Waypoint waypoint;
        public int depth;

        WaypointDepth(Waypoint waypoint, int depth) {
            this.depth = depth;
            this.waypoint = waypoint;
        }
    }
}