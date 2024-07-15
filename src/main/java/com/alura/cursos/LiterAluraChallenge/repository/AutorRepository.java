package com.alura.cursos.LiterAluraChallenge.repository;
import com.alura.cursos.LiterAluraChallenge.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor,Long> {
    // Consulta para listar todos los autores registrados
    List<Autor> findAll();

    // Consulta para listar autores vivos en un determinado año
    @Query("SELECT a FROM Autor a WHERE a.fechaDeNacimiento <= :anio AND (a.fechaDefallecimiento IS NULL OR a.fechaDefallecimiento > :anio)")
    List<Autor> listarAutoresVivosEnAnio(@Param("anio") String anio);
}
