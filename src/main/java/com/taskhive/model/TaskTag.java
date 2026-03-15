package com.taskhive.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "task_tags")
@Getter
@Setter
public class TaskTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_tag_id")
    private Long taskTagId;

    @ManyToOne
    @JoinColumn(nullable = false, name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(nullable = false, name = "tag_id")
    private Tag tag;
}
