package com.tuxdave.erasmusapp.ws_segnalazioni.service;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.StatoSegnalazione;
import com.tuxdave.erasmusapp.ws_segnalazioni.repository.StatoSegnalazioneRepository;
import jdk.jfr.Frequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatoSegnalazioneServiceImpl implements StatoSegnalazioneService {

    @Autowired
    StatoSegnalazioneRepository statoSegnalazioneRepository;

    @Override
    public StatoSegnalazione findStatoSegnalazioneById(int id) {
        return statoSegnalazioneRepository.findStatoSegnalazioneById(id);
    }

    @Override
    public boolean doesThisIdExists(int id) {
        return statoSegnalazioneRepository.findStatoSegnalazioneById(id) != null;
    }
}
