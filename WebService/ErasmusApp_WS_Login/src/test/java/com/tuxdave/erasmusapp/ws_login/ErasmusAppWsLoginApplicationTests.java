package com.tuxdave.erasmusapp.ws_login;

import com.tuxdave.erasmusapp.ws_login.entity.Ruolo;
import com.tuxdave.erasmusapp.ws_login.entity.Utente;
import com.tuxdave.erasmusapp.ws_login.service.RuoloService;
import com.tuxdave.erasmusapp.ws_login.service.UtenteService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@ContextConfiguration(classes = ErasmusAppWsLoginApplication.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Rollback(value = false)
class ErasmusAppWsLoginApplicationTests {

    @Autowired
    BCryptPasswordEncoder encoder;

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
        Utente u1 = new Utente();
        u1.setUsername("Utente1");
        u1.setPassword("Passwordd");
        Ruolo r = new Ruolo();
        r.setNome("Ruolo1");
        ruoloService.saveOrUpdate(r);
        r = new Ruolo();
        r.setNome("Ruolo2");
        ruoloService.saveOrUpdate(r);
        utenteService.saveOrUpdate(u);
        utenteService.saveOrUpdate(u1);
    }

    @Test
    @Order(2)
    void t2() {
        Utente u = utenteService.findUtenteByUsername("TuxDave");
        if(utenteService.checkPassword(u, "Passwordd")){
            return;
        }else{
            throw new RuntimeException("Password Utente non corretta!");
        }
    }

    @Test
    @Order(3)
    void t3() {
        Utente u = utenteService.findUtenteByUsername("TuxDave");
        Utente u1 = utenteService.findUtenteByUsername("Utente1");
        Ruolo r = ruoloService.findRuoloByNome("Ruolo1");
        u.getRuoli().add(r);
        u1.getRuoli().add(r);
        r = ruoloService.findRuoloByNome("Ruolo2");
        u.getRuoli().add(r);
        System.out.println("Ruoli: " + u.getRuoli().size());
        utenteService.saveOrUpdate(u);
        utenteService.saveOrUpdate(u1);
    }

    @Test
    @Order(4)
    void t4() {
        t2();
    }

    @Test
    @Order(5)
    void t5(){
        List<Ruolo> ruoli = utenteService.findUtenteByUsername("TuxDave").getRuoli();
        if(ruoli.size() != 2){
            throw new RuntimeException("Ruoli non inseriti correttamente!");
        }
    }

    @Test
    @Order(6)
    void t6() {
        List<Utente> u = utenteService.findAllUtenti();
        List<Ruolo> r = ruoloService.findAllRuoli();
        r.forEach((ru) -> {ruoloService.delete(ru);});
        u.forEach((ut) -> {utenteService.delete(ut);});
    }

}
