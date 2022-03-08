package com.tuxdave.erasmusapp.ws_segnalazioni;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Comune;
import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Segnalazione;
import com.tuxdave.erasmusapp.ws_segnalazioni.repository.SegnalazioneRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional(readOnly = true)
class ErasmusAppWsSegnalazioniApplicationTests {

    @Autowired
    SegnalazioneRepository segnalazioneRepository;

    @Test
    void contextLoads() {
        Comune c = new Comune();
        c.setCodiceCatastale("A861");
        System.out.println(segnalazioneRepository.searchSegnalazioneByStatoSegnalazione(Segnalazione.StatoSegnalazione.IN_RISOLUZIONE.getValue()));
    }

}
