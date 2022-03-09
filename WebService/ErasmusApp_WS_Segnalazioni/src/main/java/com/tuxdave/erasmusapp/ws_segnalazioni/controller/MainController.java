package com.tuxdave.erasmusapp.ws_segnalazioni.controller;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Comune;
import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Segnalazione;
import com.tuxdave.erasmusapp.ws_segnalazioni.repository.SegnalazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Transactional(readOnly = false)
@RequestMapping("/")
public class MainController {
    //TODO: cancellare questo controller prima del deploy perchè non so fare i tests

    @Autowired
    SegnalazioneRepository segnalazioneRepository;

    @GetMapping
    public ResponseEntity<Segnalazione> doTest(){
        Comune c = new Comune();
        c.setCodiceCatastale("A861");
        segnalazioneRepository.setStatoSegnalazione(
                Segnalazione.StatoSegnalazione.DA_RISOLVERE.getValue(),
                segnalazioneRepository.findSegnalazioneById(1L)
        );
        System.out.println(segnalazioneRepository.findSegnalazioneById(1L));
        return null;
    }
}
