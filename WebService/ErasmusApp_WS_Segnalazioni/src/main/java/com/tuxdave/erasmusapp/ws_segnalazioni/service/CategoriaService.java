package com.tuxdave.erasmusapp.ws_segnalazioni.service;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Categoria;

import java.util.List;

public interface CategoriaService {
    Categoria findCategoriaById(Integer id);
    Categoria findCategoriaByNome(String nome);
    List<Categoria> searchCategoriaByNome(String nome);
}
