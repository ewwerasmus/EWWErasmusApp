package com.tuxdave.erasmusapp.ws_segnalazioni.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/segnalazione")
@Log
@Api(value = "Segnalazione Controller", tags = "Controller di gestione di tutte le segnalazioni e dati ad esse dipendenti.")
public class SegnalazioneController {

    @Autowired
    SegnalazioneService segnalazioneService;

    @ApiOperation(
            value = "Seleziona tutte le segnalazioni.",
            notes = "Restituisce i dati semplici della segnalazione in formato JsonArray.",
            response = List.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tutte le categorie trovate!"),
    })
    @GetMapping
    public ResponseEntity<List<Segnalazione>> getAllSegnalazioni(){
        log.info("Richieste tutte le segnalazioni...");
        List<Segnalazione> ls = segnalazioneService.findAll();
        log.info("Rilasciata una lista di " + ls.size() + " segnalazioni!");
        return new ResponseEntity<List<Segnalazione>>(ls, HttpStatus.OK);
    }
}
