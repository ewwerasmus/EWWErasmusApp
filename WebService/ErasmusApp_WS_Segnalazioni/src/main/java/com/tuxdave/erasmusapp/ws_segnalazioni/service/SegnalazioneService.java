package com.tuxdave.erasmusapp.ws_segnalazioni.service;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Segnalazione;

import java.util.List;

public interface SegnalazioneService {

    Segnalazione findSegnalazioneById(Long id);
    List<Segnalazione> searchSegnalazioneByComune_CodiceCatastale(String codiceCatastale);
    List<Segnalazione> searchSegnalazioneByCategoria_Id( Integer id);
    List<Segnalazione> searchSegnalazioneByStatoSegnalazione(Integer stato);
    void setStatoSegnalazione(Integer newStato, Segnalazione segnalazione);
    void save(Segnalazione s);
}
