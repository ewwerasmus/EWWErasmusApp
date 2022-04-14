package com.tuxdave.erasmusapp.ws_segnalazioni.service;

import com.tuxdave.erasmusapp.shared.exception.classic.SaveException;
import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Categoria;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoriaService {
    Categoria findCategoriaById(Integer id);
    Categoria findCategoriaByNome(String nome);
    List<Categoria> searchCategoriaByNome(String nome);
    List<Categoria> findAll();
    void save(Categoria c) throws SaveException;
}
