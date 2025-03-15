package com.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence")
    @SequenceGenerator(name = "id_sequence", sequenceName = "task_sequence", allocationSize = 1)
    private Long id;
    @Column(name="title")
    private String title;
    @Column(name="description")
    private String description;
    @Column(name="completed")
    private boolean completed;
    @Column(name="datecreated")
    private LocalDateTime dateCreated;
    @Column(name="dateupdated")
    private LocalDateTime dateUpdated;
    @Column(name="datefinished")
    private LocalDateTime dateFinished;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;


    @PrePersist
    protected void onCreate() {
        dateCreated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dateUpdated = LocalDateTime.now();
    }
}
