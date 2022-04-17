package com.tuxdave.erasmusapp.ws_login;

import com.tuxdave.erasmusapp.ws_login.entity.Ruolo;
import com.tuxdave.erasmusapp.ws_login.entity.Utente;
import com.tuxdave.erasmusapp.ws_login.repository.RuoloRepository;
import com.tuxdave.erasmusapp.ws_login.service.RuoloService;
import com.tuxdave.erasmusapp.ws_login.service.UtenteService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;

@ContextConfiguration(classes = ErasmusAppWsLoginApplication.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Rollback(value = false)
class ErasmusAppWsLoginApplicationTests {

    @Autowired
    UtenteService utenteService;

    @Autowired
    RuoloService ruoloService;

    @Test
    @Order(1)
    void t1() {
        Utente u = new Utente();
        u.setUsername("TuxDave");
        u.setPassword("Passwordd");
        Ruolo r = new Ruolo();
        r.setNome("Ruolo1");
        ruoloService.saveOrUpdate(r);
        utenteService.saveOrUpdate(u);
    }

    @Test
    @Order(2)
    void t2() {
        Utente u = utenteService.findUtenteByUsername("TuxDave");
        Ruolo r = ruoloService.findRuoloByNome("Ruolo2");
        u.getRuoli().add(r);
        utenteService.saveOrUpdate(u);
    }

    @Test
    @Order(3)
    void t3() {
        Utente u = new Utente();
        u.setUsername("TuxDave");
        u.setPassword("Password");
        Ruolo r = new Ruolo();
        r.setNome("Ruolo1");
        ruoloService.delete(r);
        utenteService.delete(u);
    }

}
