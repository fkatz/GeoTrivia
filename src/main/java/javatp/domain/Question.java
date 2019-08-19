package javatp.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Questions")
public class Question {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String content;

    @ManyToOne
    @JoinColumn(name="poi_id")
    @JsonIgnore
    private POI poi;

    @OneToMany(mappedBy = "question")
    @JsonIgnore
    private List<Answer> answers;

    public Question() {}    
    public Question(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @JsonIgnore
    public POI getPoi() {
        return poi;
    }

    @JsonIgnore
    public void setPoi(POI poi) {
        this.poi = poi;
    }
   
    @JsonIgnore
    public List<Answer> getAnswers() {
        return answers;
    }

    @JsonIgnore
    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
