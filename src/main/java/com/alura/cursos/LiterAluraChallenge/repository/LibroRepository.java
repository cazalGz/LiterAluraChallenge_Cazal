package com.alura.cursos.LiterAluraChallenge.repository;
import com.alura.cursos.LiterAluraChallenge.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Long>{
    // Consulta para listar todos los libros registrados
    List<Libro> findAll();

    // Consulta para listar todos los libros en un idioma específico
    @Query("SELECT l FROM Libro l JOIN l.idiomas i WHERE i = :idioma")
    List<Libro> buscarLibrosPorIdioma(@Param("idioma") String idioma);
}
