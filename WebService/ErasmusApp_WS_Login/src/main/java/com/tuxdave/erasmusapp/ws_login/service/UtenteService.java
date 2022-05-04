package com.tuxdave.erasmusapp.ws_login.service;

import com.tuxdave.erasmusapp.shared.exception.custom.NotFoundException;
import com.tuxdave.erasmusapp.ws_login.entity.Utente;

import java.util.List;

public interface UtenteService {
    List<Utente> findAllUtenti();
    Utente findUtenteByUsername(String username);
    void saveOrUpdate(Utente u);
    void delete(Utente u);
    List<Utente> searchUtenteByRuoloNome(String r) throws NotFoundException;
    List<Utente> searchUtenteBySyncRuoliNome(String[] nomi) throws NotFoundException;
    boolean checkPassword(Utente user, String passwd);
}
