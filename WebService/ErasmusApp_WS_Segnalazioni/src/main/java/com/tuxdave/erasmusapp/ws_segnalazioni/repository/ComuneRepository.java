package com.tuxdave.erasmusapp.ws_segnalazioni.repository;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Comune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComuneRepository extends JpaRepository<Comune, String> {
    @Query("SELECT C FROM Comune C WHERE C.nome = :nome")
    Comune findComuneByNome(@Param("nome") String nome);

    @Query("SELECT C FROM Comune C WHERE C.nome LIKE %:nome%")
    List<Comune> searchComuneByNomeLike(@Param("nome") String nome);

    @Query("SELECT C FROM Comune C WHERE C.provincia = :provincia")
    List<Comune> searchComuneByProvincia(@Param("provincia") String provincia);
}