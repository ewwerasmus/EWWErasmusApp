package com.tuxdave.erasmusapp.ws_login.controller;

import com.tuxdave.erasmusapp.shared.exception.custom.NotFoundException;
import com.tuxdave.erasmusapp.ws_login.entity.Utente;
import com.tuxdave.erasmusapp.ws_login.service.UtenteService;
import io.swagger.annotations.*;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
@RequestMapping("/api/utente")
@Log
@Api(value = "Utente Controller", tags = "Controller di gestione di tutti gli utenti e dati annessi.")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @ApiOperation(
            value = "Seleziona un Utente in base all'USERNAME.",
            notes = "Restituisce i dati dell'Utente in formato JsonObject.",
            response = Utente.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Utente trovato!"),
            @ApiResponse(code = 404, message = "Utente non trovato!"),
    })
    @GetMapping("query/username/{username}")
    @SneakyThrows
    public ResponseEntity<Utente> queryUtenteByUsername(
            @ApiParam(value = "Username dell'Utente da ricercare.", required = true)
            @PathVariable("username")
            String username
    ){
        log.info("Richiesto utente con USERNAME=" + username);
        Utente u = utenteService.findUtenteByUsername(username);
        if(u == null){
            String errMsg = "Nessun utente trovato con username: " + username;
            log.warning(errMsg);
            throw new NotFoundException(errMsg);
        }else{
            log.info("Rispondo con l'utente richiesto");
            return new ResponseEntity<Utente>(u, HttpStatus.OK);
        }
    }

    @ApiOperation(
            value = "Seleziona tutti gli Utenti.",
            notes = "Restituisce i dati dell'Utente in formato JsonArray.",
            response = List.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Utenti trovati!"),
    })
    @GetMapping("query")
    @SneakyThrows
    public ResponseEntity<List<Utente>> queryAllUtenti(){
        log.info("Richiesta la lista di tutti gli utenti");
        List<Utente> us = utenteService.findAllUtenti();
        log.info("Rilascio una lista di " + us.size() + " utenti!");
        return new ResponseEntity<>(us, HttpStatus.OK);
    }
}
