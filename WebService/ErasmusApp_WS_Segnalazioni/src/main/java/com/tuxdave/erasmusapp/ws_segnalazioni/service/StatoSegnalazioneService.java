package com.tuxdave.erasmusapp.ws_segnalazioni.service;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.StatoSegnalazione;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

public interface StatoSegnalazioneService {
    StatoSegnalazione findStatoSegnalazioneById(int id);
    boolean doesThisIdExists(int id);
}
