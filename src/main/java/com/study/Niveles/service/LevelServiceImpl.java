package com.study.Niveles.service;

import com.study.Niveles.model.Level;
import com.study.Niveles.repository.LevelRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class LevelServiceImpl implements LevelService{

    private final LevelRepository levelRepository;

    public LevelServiceImpl(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Override
    @Transactional
    public Level insert(Level level) {
        return levelRepository.save(level);
    }

    @Override
    @Transactional
    public Level update(Level level) {
        return levelRepository.save(level);
    }

    @Override
    @Transactional
    public void delete(Long levelId) {
        levelRepository.deleteById(levelId);
    }

    @Override
    @Transactional
    public Level findById(Long levelId) {
        return levelRepository.findById(levelId).orElse(null);
    }

    @Override
    @Transactional
    public Level findByName(String name) {
        return levelRepository.findByName(name);
    }

    @Override
    @Transactional
    public Collection<Level> findAll() {
        return levelRepository.findAll();
    }
}
