package com.tuxdave.erasmusapp.ws_segnalazioni;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Utente;
import com.tuxdave.erasmusapp.ws_segnalazioni.repository.ComuneRepository;
import com.tuxdave.erasmusapp.ws_segnalazioni.service.ComuneService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = ErasmusAppWsSegnalazioniApplication.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Rollback(value = false)
public class Test1 {

    @Autowired
    private ComuneService comuneService;

    @Test
    @Order(1)
    public void t1(){
        Utente utente1 = new Utente();
        utente1.setUsername("TuxDave");
        System.out.println(comuneService.getComuneByUtenteUsername("TuxDave").get(0).getCodiceCatastale());
    }

}
