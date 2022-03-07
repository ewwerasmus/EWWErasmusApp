package com.tuxdave.erasmusapp.ws_segnalazioni;

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
        System.out.println(segnalazioneRepository.findSegnalazioneById(1L));
    }

}
