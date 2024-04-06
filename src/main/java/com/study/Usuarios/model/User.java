package com.study.Usuarios.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.Cursos.model.Logro;
import com.study.Niveles.model.Level;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usertId;

    @Size(min = 2,max = 40)
    @Column(length = 40, nullable = false)
    private String firstname;

    @Size(min = 2,max = 40)
    @Column(length = 40, nullable = false)
    private String lastname;

    @Email
    @Column(length = 60, nullable = false, unique = true)
    private String email;

    @Size(min = 5, max = 30)
    @Column(length = 30, nullable = false, unique = true)
    private String username;

    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{10,}$")
    @Column(length = 200, nullable = false)
    private String password;

    @Pattern(regexp="^[0-9]{9}$")
    @Column(length = 9, nullable = false)
    private String phone;

    @PositiveOrZero(message = "Experience no puede ser negativo")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int stars = 0;

    @PositiveOrZero(message = "Experience no puede ser negativo")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private double experience = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "level",referencedColumnName = "levelId")
    @JsonIgnore
    private Level level;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Role.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name="usert_id"), inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "usertId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CursoDesbloqueado> cursosDesbloqueados = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_logro", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "logro_id"))
    private Set<Logro> logros = new HashSet<>();

}
