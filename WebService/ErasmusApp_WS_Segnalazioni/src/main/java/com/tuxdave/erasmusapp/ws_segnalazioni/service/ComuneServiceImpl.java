package com.tuxdave.erasmusapp.ws_segnalazioni.service;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Comune;
import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Utente;
import com.tuxdave.erasmusapp.ws_segnalazioni.repository.ComuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ComuneServiceImpl implements ComuneService {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private ComuneRepository comuneRepository;

    @Override
    public Comune findComuneByCodiceCatastale(String cc) {
        return comuneRepository.findById(cc).get();
    }

    @Override
    public Comune findComuneByNome(String nome) {
        return comuneRepository.findComuneByNome(nome);
    }

    @Override
    public List<Comune> findAll() {
        return comuneRepository.findAll();
    }

    @Override
    public List<Comune> searchComuneByProvincia(String p) {
        return comuneRepository.searchComuneByProvincia(p);
    }

    @Override
    public List<Comune> searchComuneByNomeLike(String nome) {
        return comuneRepository.searchComuneByNomeLike(nome);
    }

    @Override
    public List<Comune> getComuneByUtenteUsername(String utenteUsername) {
        return comuneRepository.getComuneByUtente(utenteService.findUtenteByUsername(utenteUsername));
    }
}
