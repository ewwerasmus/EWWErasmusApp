package com.tuxdave.erasmusapp.ws_login.service;

import com.tuxdave.erasmusapp.ws_login.entity.Utente;

import java.util.List;

public interface UtenteService {
    List<Utente> findAllUtenti();
    Utente findUtenteByUsername(String username);
    void saveOrUpdate(Utente u);
    void delete(Utente u);

    boolean checkPassword(Utente user, String passwd);
}
