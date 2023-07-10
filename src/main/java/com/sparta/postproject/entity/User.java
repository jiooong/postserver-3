package com.sparta.postproject.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User extends BaseTimeEntity{

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;
}
