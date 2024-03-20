package com.study.Niveles.repository;

import com.study.Niveles.model.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {

    public  abstract Level findByName(String name);
}
