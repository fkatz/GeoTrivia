package javatp.domain;

import java.util.List;

import javax.persistence.CascadeType;
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

    @ManyToOne()
    @JoinColumn(name="poi_id")
    @JsonIgnore
    private POI poi;

    @OneToMany(mappedBy = "question",cascade = { CascadeType.PERSIST })
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
    public POI getPOI() {
        return poi;
    }

    @JsonIgnore
    public void setPOI(POI poi) {
        this.poi = poi;
    }
   
    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        for (Answer answer : answers) {
            answer.setQuestion(this);
        }
        this.answers = answers;
    }

    @JsonIgnore
    public Answer getCorrectAnswer(){
        Answer correct = null;
        for (Answer answer : answers) {
            if(answer.getIsCorrect()) {
                correct = answer;
                break;
            }
        }
        return correct;
    }
}
