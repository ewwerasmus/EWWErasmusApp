package com.tuxdave.erasmusapp.ws_login.controller;

import com.tuxdave.erasmusapp.ws_login.entity.Ruolo;
import com.tuxdave.erasmusapp.ws_login.service.RuoloService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ruolo")
@Log
@Api(value = "Ruolo Controller", tags = "Controller di gestione di tutti i Ruoli e dati annessi.")
public class RuoloController {

    @Autowired
    private RuoloService ruoloService;

    @ApiOperation(
            value = "Seleziona tutti i Ruoli.",
            notes = "Restituisce i dati dell'Utente in formato JsonArray.",
            response = List.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ruoli trovati!"),
    })
    @GetMapping("query")
    public ResponseEntity<List<Ruolo>> queryAllRuoli(){
        log.info("Richiesti tutti i ruoli.");
        List<Ruolo> rs = ruoloService.findAllRuoli();
        log.info("Rispondo con " + rs.size() + " ruoli!");
        return new ResponseEntity<List<Ruolo>>(rs, HttpStatus.OK);
    }
}
