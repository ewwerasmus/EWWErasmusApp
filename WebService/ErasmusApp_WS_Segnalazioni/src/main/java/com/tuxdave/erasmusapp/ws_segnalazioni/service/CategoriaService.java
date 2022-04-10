package com.tuxdave.erasmusapp.ws_segnalazioni.service;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Categoria;
import com.tuxdave.erasmusapp.ws_segnalazioni.exception.classic.SaveException;

import java.util.List;

public interface CategoriaService {
    Categoria findCategoriaById(Integer id);
    Categoria findCategoriaByNome(String nome);
    List<Categoria> searchCategoriaByNome(String nome);
    List<Categoria> findAll();
    void save(Categoria c) throws SaveException;
}
