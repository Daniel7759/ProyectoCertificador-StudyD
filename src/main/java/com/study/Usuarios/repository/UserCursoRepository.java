package com.study.Usuarios.repository;

import com.study.Usuarios.model.UserCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCursoRepository extends JpaRepository<UserCurso, Long> {

    List<UserCurso> findByUserUsertId(Long usertId);
}
