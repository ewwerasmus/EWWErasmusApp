package com.tuxdave.erasmusapp.ws_segnalazioni;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Comune;
import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Segnalazione;
import com.tuxdave.erasmusapp.ws_segnalazioni.repository.SegnalazioneRepository;
import com.tuxdave.erasmusapp.ws_segnalazioni.repository.StatoSegnalazioneRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional(readOnly = false)
@Rollback(value = false)
class ErasmusAppWsSegnalazioniApplicationTests {

    @Autowired
    SegnalazioneRepository segnalazioneRepository;

    @Autowired
    StatoSegnalazioneRepository statoSegnalazioneRepository;

    @Test
    void contextLoads() {
        Comune c = new Comune();
        c.setCodiceCatastale("A861");
        segnalazioneRepository.setStatoSegnalazione(
            statoSegnalazioneRepository.findByLabel("IN RISOLUZIONE"),
            segnalazioneRepository.findSegnalazioneById(1L)
        );
        System.out.println(segnalazioneRepository.findSegnalazioneById(1L).getStatoSegnalazione());
    }

}
