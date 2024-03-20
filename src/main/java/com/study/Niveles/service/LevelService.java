package com.study.Niveles.service;

import com.study.Niveles.model.Level;

import java.util.Collection;

public interface LevelService {

    public abstract Level insert(Level level);
    public abstract Level update(Level level);
    public abstract void delete(Long levelId);
    public abstract Level findById(Long levelId);
    public abstract Level findByName(String name);
    public abstract Collection<Level> findAll();
}
