package com.study.Niveles.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.Usuarios.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "levels")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long levelId;
    private String name;
    private String imageUrl;
    private double xpNeeded;
    private double bonusStar;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "level", cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();
}
