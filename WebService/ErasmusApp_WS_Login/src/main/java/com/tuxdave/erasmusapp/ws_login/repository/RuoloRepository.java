package com.tuxdave.erasmusapp.ws_login.repository;

import com.tuxdave.erasmusapp.ws_login.entity.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, String> {
    @Query("SELECT COUNT(R) FROM Ruolo R WHERE R.nome = :nome")
    int countRuoloByNome(@Param("nome") String nome);

    @Query("SELECT R FROM Ruolo R WHERE R.nome IN :nomi")
    List<Ruolo> findRuoliByNomi(@Param("nomi") String[] nomi);
}
