package com.tuxdave.erasmusapp.ws_segnalazioni.repository;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Segnalazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;

public interface SegnalazioneRepository extends JpaRepository<Segnalazione, Long> {

    @Query("SELECT S FROM Segnalazione S WHERE S.id = (:id)")
    Segnalazione findSegnalazioneById(@Param("id") Long id);
}
