package com.study.Usuarios.repository;

import com.study.Usuarios.model.TokenFCM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenFCMRepository extends JpaRepository<TokenFCM, Long> {

    TokenFCM findByUsertIdAndToken(Long usertId, String token);
}
