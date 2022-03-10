package com.tuxdave.erasmusapp.ws_segnalazioni.controller;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Categoria;
import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Comune;
import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Coordinata;
import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Segnalazione;
import com.tuxdave.erasmusapp.ws_segnalazioni.service.SegnalazioneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional(readOnly = false)
@RequestMapping("/")
@Log
@Api(value = "Anime Controller", tags = "Controller gestione di tutti gli Anime e dati relativi.")
public class MainController {
    //TODO: cancellare questo controller prima del deploy perchè non so fare i tests

    @Autowired
    SegnalazioneService segnalazioneService;

    @ApiOperation(
            value = "Seleziona tutti gli anime.",
            notes = "Restituisce i dati semplici dell'anime in formato JsonArray.",
            response = List.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tutti gli Anime trovati!"),
    })
    @GetMapping
    public ResponseEntity<Segnalazione> doTest(){
        Segnalazione s = new Segnalazione();

        Comune c = new Comune();
        c.setCodiceCatastale("A860");
        s.setComune(c);

        Categoria c1 = new Categoria();
        c1.setId(2);
        s.setCategoria(c1);

        Coordinata c2 = new Coordinata();
        c2.setLatitudine(4.0);
        c2.setLongitudine(4.0);
        s.setCoordinata(c2);

        s.setDescrizione("Descrizione Prova 2");
        s.setUrgenza(8);
//        s.setStatoSegnalazione(1);

        segnalazioneService.save(s);
        return new ResponseEntity<Segnalazione>(segnalazioneService.findSegnalazioneById(2L), HttpStatus.OK);
    }
}
