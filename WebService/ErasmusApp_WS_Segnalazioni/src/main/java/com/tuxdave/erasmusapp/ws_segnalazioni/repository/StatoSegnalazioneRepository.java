package com.tuxdave.erasmusapp.ws_segnalazioni.repository;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.StatoSegnalazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StatoSegnalazioneRepository extends JpaRepository<StatoSegnalazione, Integer> {

    @Query("SELECT S FROM StatoSegnalazione S WHERE S.label = (:label)")
    StatoSegnalazione findByLabel(@Param("label") String label);
}
