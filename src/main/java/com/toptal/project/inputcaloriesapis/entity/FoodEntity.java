package com.toptal.project.inputcaloriesapis.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "food")
public class FoodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "food_id", unique = true, nullable = false)
    private UUID foodId;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "time", nullable = false)
    private String time;

    @Column(name = "food", nullable = false)
    private String food;

    @Column(name = "calorie", nullable = false)
    private Integer calories;

    @Column(name = "within_limit", nullable = false)
    private Boolean withinLimit;
}
