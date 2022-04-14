package com.tuxdave.erasmusapp.ws_login.service;

import com.tuxdave.erasmusapp.ws_login.entity.Ruolo;

import java.util.List;

public interface RuoloService {
    List<Ruolo> findAllRuoli();
    Ruolo findRuoloByNome(String nome);
    void saveOrUpdate(Ruolo r);
    void delete(Ruolo r);
}
