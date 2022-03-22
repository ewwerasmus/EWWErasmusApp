package com.tuxdave.erasmusapp.ws_segnalazioni.repository;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Segnalazione;
import com.tuxdave.erasmusapp.ws_segnalazioni.entity.StatoSegnalazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SegnalazioneRepository extends JpaRepository<Segnalazione, Long> {
    @Query("SELECT S FROM Segnalazione S WHERE S.id = (:id)")
    Segnalazione findSegnalazioneById(@Param("id") Long id);

    @Query("SELECT NEW Segnalazione(S.id, S.descrizione, S.urgenza, S.statoSegnalazione) from Segnalazione S WHERE S.id = (:id)")
    Segnalazione findSegnalazioneEssentialById(@Param("id") Long id);

    @Query("SELECT NEW Segnalazione(S.id, S.descrizione, S.urgenza, S.statoSegnalazione) from Segnalazione S")
    List<Segnalazione> findAllEssential();

    @Query("SELECT NEW Segnalazione(S.id, S.descrizione, S.urgenza, S.statoSegnalazione) FROM Segnalazione S WHERE S.comune.codiceCatastale = (:codiceCatastale)")
    List<Segnalazione> searchSegnalazioneEssentialByComune_CodiceCatastale(@Param("codiceCatastale") String codiceCatastale);

    @Query("SELECT NEW Segnalazione(S.id, S.descrizione, S.urgenza, S.statoSegnalazione) FROM Segnalazione S WHERE S.categoria.id = (:id)")
    List<Segnalazione> searchSegnalazioneEssentialByCategoria_Id(@Param("id") Integer id);

    @Query("SELECT NEW Segnalazione(S.id, S.descrizione, S.urgenza, S.statoSegnalazione) FROM Segnalazione S WHERE S.statoSegnalazione = (:stato)")
    List<Segnalazione> searchSegnalazioneEssentialByStatoSegnalazione(@Param("stato") StatoSegnalazione stato);

    @Query("SELECT S " +
            "FROM Segnalazione S JOIN FETCH S.coordinata " +
            "WHERE (S.coordinata.latitudine BETWEEN (:lat - :around) AND (:lat + :around)) " +
            "   AND (S.coordinata.longitudine BETWEEN (:lon - :around) AND (:lon + :around))") //TODO: review this
    List<Segnalazione> searchSegnalazioneByCoordinataAround(
            @Param("lat") Double lat,
            @Param("lon") Double lon,
            @Param("around") Double around
    );

    @Modifying
    @Query("UPDATE Segnalazione S SET S.statoSegnalazione = :newStato WHERE S = :which")
    void setStatoSegnalazione(@Param("newStato") StatoSegnalazione newStato, @Param("which") Segnalazione segnalazione);
}
