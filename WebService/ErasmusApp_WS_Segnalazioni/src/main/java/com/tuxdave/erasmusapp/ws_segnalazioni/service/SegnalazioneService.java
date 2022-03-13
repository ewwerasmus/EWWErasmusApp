package com.tuxdave.erasmusapp.ws_segnalazioni.service;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Segnalazione;
import com.tuxdave.erasmusapp.ws_segnalazioni.entity.StatoSegnalazione;
import com.tuxdave.erasmusapp.ws_segnalazioni.exceptions.classic.SaveException;

import java.util.List;

public interface SegnalazioneService {

    List<Segnalazione> findAll();
    Segnalazione findSegnalazioneById(Long id);
    List<Segnalazione> searchSegnalazioneByComune_CodiceCatastale(String codiceCatastale);
    List<Segnalazione> searchSegnalazioneByCategoria_Id( Integer id);
    List<Segnalazione> searchSegnalazioneByStatoSegnalazione(StatoSegnalazione stato);
    void setStatoSegnalazione(StatoSegnalazione newStato, Segnalazione segnalazione);
    void save(Segnalazione s) throws SaveException;
}
