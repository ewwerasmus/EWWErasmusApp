package com.tuxdave.erasmusapp.ws_segnalazioni.service;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Utente;

public interface UtenteService {
    Utente findUtenteByUsername(String username);
}
