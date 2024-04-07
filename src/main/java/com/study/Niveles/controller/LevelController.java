package com.study.Niveles.controller;

import com.study.Niveles.model.Level;
import com.study.Niveles.service.LevelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "levels")
public class LevelController {

    private final LevelService levelService;

    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            Collection<Level> levelsDB = levelService.findAll();
            return new ResponseEntity<>(levelsDB, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{levelId}")
    public ResponseEntity<?> getById(@PathVariable Long levelId){
        try{
            Level levelDB= levelService.findById(levelId);
            return new ResponseEntity<>(levelDB, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createLevel(@RequestBody Level level){
        try {
            Level existingLevel = levelService.findByName(level.getName());
            if (existingLevel!=null){
                return new ResponseEntity<>(existingLevel, HttpStatus.OK);
            }

            Level levelDB = levelService.insert(level);
            return new ResponseEntity<>(levelDB, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
