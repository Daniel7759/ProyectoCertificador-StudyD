package com.study.Cursos.controller;

import com.study.Cursos.model.Curso;
import com.study.Cursos.model.Materia;
import com.study.Cursos.service.CursoService;
import com.study.Cursos.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private MateriaService materiaService;

    @GetMapping
    public ResponseEntity<?> getAllCursos(@RequestParam(required = false) String tag){
        try {
            Collection<Curso> cursosDB = cursoService.findAll();

            // Filtrar cursos por tag si se proporciona el parámetro tag
            if (tag != null && !tag.isEmpty()) {
                Collection<Curso> cursosFiltrados = cursosDB.stream()
                        .filter(curso -> curso.getTag().name().equalsIgnoreCase(tag))
                        .collect(Collectors.toList());
                return new ResponseEntity<>(cursosFiltrados, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(cursosDB, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{cursoId}")
    public ResponseEntity<?> getCursoById(@PathVariable Long cursoId){
        try{
            Curso cursoDB= cursoService.findById(cursoId);
            return new ResponseEntity<>(cursoDB,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createCurso(@RequestBody Curso curso){
        try{
            Curso existingCurso=cursoService.findByTitle(curso.getTitle());

            if (existingCurso!=null){
                return new ResponseEntity<>(existingCurso, HttpStatus.OK);
            }
            Curso cursoDB=cursoService.insert(curso);
            return new ResponseEntity<>(cursoDB,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/recientes")
    public ResponseEntity<?> getAllCursosByFechaCreacion() {
        try {
            Collection<Curso> cursosDB = cursoService.findAllByFechaCreacionDesc();
            return new ResponseEntity<>(cursosDB, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/materia/{materiaId}")
    public ResponseEntity<?> getCursosByMateriaId(@PathVariable Long materiaId) {
        try {
            Collection<Curso> cursosDB = cursoService.findAllByMateriaId(materiaId);
            return new ResponseEntity<>(cursosDB, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/opcionales")
    public ResponseEntity<?> getCursosByMateriaOpcional(){
        try{
            Collection<Curso> cursosDB = materiaService.obtenerCursosDeMateriasOpcionales();
            return new ResponseEntity<>(cursosDB, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
