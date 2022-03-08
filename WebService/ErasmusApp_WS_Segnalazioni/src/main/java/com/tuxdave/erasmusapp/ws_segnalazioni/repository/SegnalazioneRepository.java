package com.tuxdave.erasmusapp.ws_segnalazioni.repository;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Segnalazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SegnalazioneRepository extends JpaRepository<Segnalazione, Long> {
    @Query("SELECT S FROM Segnalazione S WHERE S.id = (:id)")
    Segnalazione findSegnalazioneById(@Param("id") Long id);

    @Query("SELECT S FROM Segnalazione S WHERE S.comune.codiceCatastale = (:codiceCatastale)")
    List<Segnalazione> searchSegnalazioneByComune_CodiceCatastale(@Param("codiceCatastale") String codiceCatastale);

    @Query("SELECT S FROM Segnalazione S WHERE S.categoria.id = (:id)")
    List<Segnalazione> searchSegnalazioneByCategoria_Id(@Param("id") Integer id);

    @Query("SELECT S FROM Segnalazione S WHERE S.statoSegnalazione = (:stato)")
    List<Segnalazione> searchSegnalazioneByStatoSegnalazione(@Param("stato") Integer stato);
}
