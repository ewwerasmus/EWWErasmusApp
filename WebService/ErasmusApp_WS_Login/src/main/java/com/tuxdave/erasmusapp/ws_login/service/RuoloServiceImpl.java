package com.tuxdave.erasmusapp.ws_login.service;

import com.tuxdave.erasmusapp.ws_login.entity.Ruolo;
import com.tuxdave.erasmusapp.ws_login.repository.RuoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RuoloServiceImpl implements RuoloService {

    @Autowired
    RuoloRepository ruoloRepository;

    @Override
    public List<Ruolo> findAllRuoli() {
        return ruoloRepository.findAll();
    }

    @Override
    public Ruolo findRuoloByNome(String nome) {
        Optional<Ruolo> ru = ruoloRepository.findById(nome);
        if(ru.isEmpty()) return null;
        else return ru.get();
    }

    @Override
    @Transactional(readOnly = false)
    public void saveOrUpdate(Ruolo r) {
        ruoloRepository.save(r);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Ruolo r) {
        ruoloRepository.delete(r);
    }

    @Override
    public boolean doesRuoloExist(String nome) {
        return ruoloRepository.countRuoloByNome(nome) == 1;
    }
}
