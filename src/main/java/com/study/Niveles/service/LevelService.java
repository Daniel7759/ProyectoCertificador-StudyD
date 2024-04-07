package com.study.Niveles.service;

import com.study.Niveles.model.Level;

import java.util.Collection;

public interface LevelService {

    Level insert(Level level);
    Level update(Level level);
    void delete(Long levelId);
    Level findById(Long levelId);
    Level findByName(String name);
    Collection<Level> findAll();
}
