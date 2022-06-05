package com.tuxdave.erasmusapp.ws_segnalazioni.service;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Comune;
import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Utente;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComuneService {
    Comune findComuneByCodiceCatastale(String cc);
    Comune findComuneByNome(String nome);
    List<Comune> findAll();
    List<Comune> searchComuneByProvincia(String p);
    List<Comune> searchComuneByNomeLike(String nome);

    List<Comune> getComuneByUtenteUsername(String utente);
}
