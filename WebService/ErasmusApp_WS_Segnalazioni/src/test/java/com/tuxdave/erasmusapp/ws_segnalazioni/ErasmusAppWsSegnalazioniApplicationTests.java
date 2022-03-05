package com.tuxdave.erasmusapp.ws_segnalazioni;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Segnalazione;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

@SpringBootTest
class ErasmusAppWsSegnalazioniApplicationTests {

    @Autowired
    SegnalazioneRepository segnalazioneRepository;

    @Test
    void contextLoads() {
        System.out.println(segnalazioneRepository.findSegnalazioneById(1L));
    }

}
