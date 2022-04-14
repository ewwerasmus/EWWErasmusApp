package com.tuxdave.erasmusapp.ws_segnalazioni.repository;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

    Categoria findCategoriaByNome(@Param("nome") String nome);

    @Query("SELECT C FROM Categoria C WHERE C.nome LIKE %:nome%")
    List<Categoria> searchCategoriaByNomeLike(@Param("nome") String nome);
}