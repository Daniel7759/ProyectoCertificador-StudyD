package com.study.Cursos.controller;

import com.study.Cursos.model.EnumMateria;
import com.study.Cursos.model.Materia;
import com.study.Cursos.service.MateriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "materias")
public class MateriaController {

    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @GetMapping
    public ResponseEntity<?> getAllMaterias(){
        try {
            Collection<Materia> materiasDB=materiaService.findAll();
            return new ResponseEntity<>(materiasDB, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{materiaId}")
    public ResponseEntity<?> getMateriaById(@PathVariable Long materiaId){
        try{
            Materia materiaDB= materiaService.findById(materiaId);
            return new ResponseEntity<>(materiaDB,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<?> getAllMateriaByType(@PathVariable EnumMateria type){
        try{
            Collection<Materia> materiasDB= materiaService.findAllByType(type);
            return new ResponseEntity<>(materiasDB,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createMateria(@RequestBody Materia materia){
        try{
            Materia existingMateria=materiaService.findByName(materia.getName());

            if (existingMateria!=null){
                return new ResponseEntity<>(existingMateria, HttpStatus.OK);
            }
            Materia materiaDB=materiaService.insert(materia);
            return new ResponseEntity<>(materiaDB,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
    }
}
