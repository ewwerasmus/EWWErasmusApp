package com.tuxdave.erasmusapp.ws_segnalazioni.service;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Categoria;
import com.tuxdave.erasmusapp.ws_segnalazioni.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Override
    public Categoria findCategoriaById(Integer id) {
        return categoriaRepository.findById(id).get();
    }

    @Override
    public Categoria findCategoriaByNome(String nome) {
        return categoriaRepository.findCategoriaByNome(nome);
    }

    @Override
    public List<Categoria> searchCategoriaByNome(String nome) {
        return categoriaRepository.searchCategoriaByNomeLike(nome);
    }
}
