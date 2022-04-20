package com.tuxdave.erasmusapp.ws_login.repository;

import com.tuxdave.erasmusapp.ws_login.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteReposotory extends JpaRepository<Utente, String> {

    @Query("SELECT U.password FROM Utente U WHERE U.username = :u")
    String findEncryptedPassewordByUtente(@Param("u") String u);

}
