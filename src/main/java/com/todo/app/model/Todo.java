package com.todo.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@Table(name = "todos")
public class Todo {

    public enum Priority { LOW, MEDIUM, HIGH }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private boolean completed = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority = Priority.MEDIUM;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    // ── Constructors ──────────────────────────────────────────────────────
    public Todo() {}

    // ── Getters & Setters ─────────────────────────────────────────────────
    public Long     getId()                        { return id; }
    public String   getTitle()                     { return title; }
    public void     setTitle(String title)         { this.title = title; }
    public String   getDescription()               { return description; }
    public void     setDescription(String d)       { this.description = d; }
    public boolean  isCompleted()                  { return completed; }
    public void     setCompleted(boolean c)        { this.completed = c; }
    public Priority getPriority()                  { return priority; }
    public void     setPriority(Priority p)        { this.priority = p; }
    public Instant  getCreatedAt()                 { return createdAt; }
}
