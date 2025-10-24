package com.project.learning.stayIn.entity;

import com.project.learning.stayIn.entity.enums.Gender;
import jakarta.persistence.*;
@Entity
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Integer age;
}
