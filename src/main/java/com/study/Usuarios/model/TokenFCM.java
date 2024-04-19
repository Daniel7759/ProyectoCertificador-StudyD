package com.study.Usuarios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "token_fcm")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenFCM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokeId;

    private Long usertId;

    private String token;
}
