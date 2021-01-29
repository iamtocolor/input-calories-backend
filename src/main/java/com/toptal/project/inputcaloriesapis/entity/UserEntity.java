package com.toptal.project.inputcaloriesapis.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true, nullable = false)
    private UUID userId;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String passWord;

    @Column(name = "daily_limit", nullable = false)
    private Integer dailyLimit;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<FoodEntity> foodEntities;
}
