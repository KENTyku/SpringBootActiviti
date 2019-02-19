package ru.kentyku.springbootactiviti.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity(name = "JPA_ENTITY_FIELD")
public class FieldAccessJPAEntity {

    @Id
    @Column(name = "ID_")
    private Long id;

    private String value;

    public FieldAccessJPAEntity() {
        // Empty constructor needed for JPA
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
