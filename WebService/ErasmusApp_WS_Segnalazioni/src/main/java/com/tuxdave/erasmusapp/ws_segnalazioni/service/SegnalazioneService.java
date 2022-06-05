package com.tuxdave.erasmusapp.ws_segnalazioni.service;

import com.tuxdave.erasmusapp.shared.exception.classic.SaveException;
import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Segnalazione;
import com.tuxdave.erasmusapp.ws_segnalazioni.entity.StatoSegnalazione;

import java.util.List;

public interface SegnalazioneService {
    Double AROUND_0003 = 0.0003;

    List<Segnalazione> findAll();
    List<Segnalazione> findAllEssential();
    Segnalazione findSegnalazioneById(Long id);
    Segnalazione findSegnalazioneEssentialById(Long id);
    List<Segnalazione> searchSegnalazioneByComune_CodiceCatastale(String codiceCatastale);
    List<Segnalazione> searchSegnalazioneByCategoria_Id( Integer id);
    List<Segnalazione> searchSegnalazioneByStatoSegnalazione(StatoSegnalazione stato);
    List<Segnalazione> searchSegnalazioneByCoordinateAround(Double lat, Double lon);
    List<Segnalazione> searchSegnalazioneByDescrizioneLike(String descrizione);
    List<Segnalazione> searchSegnalazioneByUrgenza(Integer urg);
    List<Segnalazione> searchSegnalazioneByUrgenzaBetween(Integer from, Integer to);
    void setStatoSegnalazione(StatoSegnalazione newStato, Segnalazione segnalazione);
    void save(Segnalazione s) throws SaveException;
    void delete(Segnalazione s);

    List<Segnalazione> findAllEssentialByEnte(String loggedUsername);
}
