package javatp.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Hints")
public class Hint {
    private Long id;
    private String description;

    public Hint() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}