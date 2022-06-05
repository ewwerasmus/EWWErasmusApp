package com.tuxdave.erasmusapp.ws_segnalazioni.service;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Utente;
import com.tuxdave.erasmusapp.ws_segnalazioni.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtenteServiceImpl implements UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;
    @Override
    public Utente findUtenteByUsername(String username) {
        Optional<Utente> u = utenteRepository.findById(username);
        return u.orElse(null);
    }
}
