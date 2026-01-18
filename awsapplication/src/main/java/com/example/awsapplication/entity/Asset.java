package com.example.awsapplication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "assets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Asset {
    @Id
    private int id;
    private String title;
}
