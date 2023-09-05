package com.example.messager.entities;

import javax.persistence.*;

@Entity
@Table(name = "conversations")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "first_id")
    private User firstId;

    @ManyToOne
    @JoinColumn(name = "second_id")
    private User secondId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFirstId() {
        return firstId;
    }

    public void setFirstId(User firstId) {
        this.firstId = firstId;
    }

    public User getSecondId() {
        return secondId;
    }

    public void setSecondId(User secondId) {
        this.secondId = secondId;
    }
}
