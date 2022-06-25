package com.tuxdave.erasmusapp.ws_segnalazioni.controller;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Comune;
import com.tuxdave.erasmusapp.ws_segnalazioni.service.ComuneService;
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
@RequestMapping("/api/comune")
@Log
@Api(value = "Comune Controller", tags = "Controller di gestione di tutti i Comuni.")
public class ComuneController {
    @Autowired
    ComuneService comuneService;

    @ApiOperation(
            value = "Seleziona tutti i comuni.",
            notes = "Restituisce i dati semplici delle categorie in formato JsonArray.",
            response = List.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tutti i comuni trovati!"),
    })
    @GetMapping("query")
    public ResponseEntity<List<Comune>> getAllComuni(){
        log.info("Richiesti tutti i comuni.");
        List<Comune> comuni = comuneService.findAll();
        log.info("Rilasciati " + comuni.size() + " comuni.");
        return new ResponseEntity<List<Comune>>(comuni, HttpStatus.OK);
    }
}
