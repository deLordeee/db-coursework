package com.example.DB.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@Entity
@Table(name = "Role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "role")
    private List<User> users;


}