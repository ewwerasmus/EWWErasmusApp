package com.tuxdave.erasmusapp.ws_login.repository;

import com.tuxdave.erasmusapp.ws_login.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtenteReposotory extends JpaRepository<Utente, String> {
    @Override
    @Query("SELECT U FROM Utente U JOIN FETCH U.ruoli WHERE U.username = (:s)")
    Utente getById(@Param("s") String s);

    @Override
    @Query("SELECT U FROM Utente U JOIN FETCH U.ruoli")
    List<Utente> findAll();
}
