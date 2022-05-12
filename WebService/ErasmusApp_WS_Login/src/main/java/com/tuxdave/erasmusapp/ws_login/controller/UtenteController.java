package com.tuxdave.erasmusapp.ws_login.controller;

import com.tuxdave.erasmusapp.shared.exception.custom.BindingException;
import com.tuxdave.erasmusapp.shared.exception.custom.DuplicateException;
import com.tuxdave.erasmusapp.shared.exception.custom.NotFoundException;
import com.tuxdave.erasmusapp.shared.validation.InfoMsg;
import com.tuxdave.erasmusapp.ws_login.entity.Ruolo;
import com.tuxdave.erasmusapp.ws_login.entity.Utente;
import com.tuxdave.erasmusapp.ws_login.service.RuoloService;
import com.tuxdave.erasmusapp.ws_login.service.UtenteService;
import io.swagger.annotations.*;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/utente")
@Log
@Api(value = "Utente Controller", tags = "Controller di gestione di tutti gli utenti e dati annessi.")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private RuoloService ruoloService;

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
    ) {
        log.info("Richiesto utente con USERNAME=" + username);
        Utente u = utenteService.findUtenteByUsername(username);
        if (u == null) {
            String errMsg = "Nessun utente trovato con username: " + username;
            log.warning(errMsg);
            throw new NotFoundException(errMsg);
        } else {
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
    public ResponseEntity<List<Utente>> queryAllUtenti() {
        log.info("Richiesta la lista di tutti gli utenti");
        List<Utente> us = utenteService.findAllUtenti();
        log.info("Rilascio una lista di " + us.size() + " utenti!");
        return new ResponseEntity<>(us, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Seleziona gli Utenti appartenenti ad un ruolo.",
            notes = "Restituisce i dati degli Utenti in formato JsonArray.",
            response = List.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Utenti trovati!"),
    })
    @GetMapping("query/ruolo/{nome}")
    @SneakyThrows
    public ResponseEntity<List<Utente>> getUtenteByRuoloNome(
            @ApiParam(value = "Il nome del ruolo in base al quale filtrare", required = true)
            @PathVariable("nome")
            String nome
    ) {
        log.info("Richiesti gli utenti appartenenti al ruolo: " + nome);
        List<Utente> ls = null;
        try {
            ls = utenteService.searchUtenteByRuoloNome(nome);
        } catch (NotFoundException e) {
            log.warning(e.getMessage());
            throw e;
        }
        log.info("Rispondo con " + ls.size() + " utenti.");
        return new ResponseEntity<List<Utente>>(ls, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Seleziona gli Utenti appartenenti a più ruoli contemporaneamente.",
            notes = "Restituisce i dati dell'Utente in formato JsonArray.",
            response = List.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Utenti trovati!"),
    })
    @GetMapping("query/ruoli/sync/{nomi}")
    public ResponseEntity<List<Utente>> getUtenteBySyncRuoloNome(
            @ApiParam(value = "I nomi dei ruoli in base al quale filtrare", required = true)
            @PathVariable("nomi")
            String[] nomi
    ) {
        log.info("Richiesti gli utenti appartenenti a n. ruoli: " + nomi.length);
        List<Utente> ls = null;
        try {
            ls = utenteService.searchUtenteBySyncRuoliNome(nomi);
        } catch (NotFoundException e) {
            RuntimeException r = new RuntimeException(e);
            log.warning(r.getMessage());
            throw r;
        }
        log.info("Rispondo con " + ls.size() + " utenti.");
        return new ResponseEntity<List<Utente>>(ls, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Rimuove un Utente.",
            notes = "L'operazione va a buon fine se i dati sono tutti validi",
            response = InfoMsg.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Utente cancellato!"),
            @ApiResponse(code = 404, message = "Utente non trovati."),
    })
    @DeleteMapping("/query/username/{username}")
    @SneakyThrows
    public ResponseEntity<InfoMsg> deleteUtente(
            @ApiParam(value = "Username dell'Utente da eliminare", required = true)
            @PathVariable("username")
            String username
    ){
        log.info("Richiesta la cancellazione dell'Utente '" + username + "'");
        Utente utente = utenteService.findUtenteByUsername(username);
        if(utente == null){
            NotFoundException e = new NotFoundException("L'Utente '" + username + "' non esiste.");
            log.warning(e.getMessage());
            throw e;
        }
        utenteService.delete(utente);
        String okMsg = "Utente '" + username + "' cancellato correttamente";
        log.info(okMsg);
        return new ResponseEntity<InfoMsg>(
                new InfoMsg(
                        new Date(),
                        okMsg
                ),
                HttpStatus.CREATED
        );
    }

    @ApiOperation(
            value = "Aggiunge un Ruolo all'utente.",
            notes = "L'operazione va a buon fine se i dati sono tutti validi",
            response = InfoMsg.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Ruolo aggiunto!"),
            @ApiResponse(code = 404, message = "Utente o Ruolo non trovati."),
            @ApiResponse(code = 409, message = "Ruolo non inserito perchè l'Utente già ci appartiene.")
    })
    @PutMapping("/query/username/{username}/add/ruolo/{nome}")
    @SneakyThrows
    public ResponseEntity<InfoMsg> addUtenteRuolo(
            @ApiParam(value = "Username dell'Utente al quale aggiungere il ruolo", required = true)
            @PathVariable("username")
            String usernameUtente,

            @ApiParam(value = "nome del Ruolo da aggiungere a questo utente.", required = true)
            @PathVariable("nome")
            String nomeRuolo
    ) {
        log.info("Richiesta l'aggiunta del ruolo '" + nomeRuolo + "' all'utente '" + usernameUtente + "'");
        Utente utente = utenteService.findUtenteByUsername(usernameUtente);
        Ruolo ruolo = ruoloService.findRuoloByNome(nomeRuolo);
        if (utente == null) {
            NotFoundException e = new NotFoundException("Impossibile trovare l'Utente '" + usernameUtente + "'");
            log.warning(e.getMessage());
            throw e;
        }
        if (ruolo == null) {
            NotFoundException e = new NotFoundException("Impossibile trovare il Ruolo '" + nomeRuolo + "'");
            log.warning(e.getMessage());
            throw e;
        }
        if (utente.getRuoli().contains(ruolo)) {
            DuplicateException e = new DuplicateException("L'utente '" + utente.getUsername() + "' possiede già questo ruolo ('" + nomeRuolo + "')");
            log.warning(e.getMessage());
            throw e;
        }
        utente.getRuoli().add(ruolo);
        utenteService.saveOrUpdate(utente);
        String okMsg = "Ruolo '" + nomeRuolo + "' aggiunto all'utente '" + usernameUtente + "'.";
        log.info(okMsg);
        return new ResponseEntity<InfoMsg>(
                new InfoMsg(
                        new Date(),
                        okMsg
                ),
                HttpStatus.CREATED
        );
    }

    @ApiOperation(
            value = "Rimuove un Ruolo all'utente.",
            notes = "L'operazione va a buon fine se i dati sono tutti validi",
            response = InfoMsg.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ruolo Rimosso!"),
            @ApiResponse(code = 404, message = "Utente o Ruolo non trovati."),
            @ApiResponse(code = 409, message = "Ruolo non rimosso perchè l'Utente non ci appartiene.")
    })
    @DeleteMapping("/query/username/{username}/remove/ruolo/{nome}")
    @SneakyThrows
    public ResponseEntity<InfoMsg> removeUtenteRuolo(
            @ApiParam(value = "Username dell'Utente al quale aggiungere il ruolo", required = true)
            @PathVariable("username")
            String usernameUtente,

            @ApiParam(value = "nome del Ruolo da aggiungere a questo utente.", required = true)
            @PathVariable("nome")
            String nomeRuolo
    ) {
        log.info("Richiesta la rimozione del ruolo '" + nomeRuolo + "' all'utente '" + usernameUtente + "'");
        Utente utente = utenteService.findUtenteByUsername(usernameUtente);
        Ruolo ruolo = ruoloService.findRuoloByNome(nomeRuolo);
        if (utente == null) {
            NotFoundException e = new NotFoundException("Impossibile trovare l'Utente '" + usernameUtente + "'");
            log.warning(e.getMessage());
            throw e;
        }
        if (ruolo == null) {
            NotFoundException e = new NotFoundException("Impossibile trovare il Ruolo '" + nomeRuolo + "'");
            log.warning(e.getMessage());
            throw e;
        }
        if (!utente.getRuoli().contains(ruolo)) {
            NotFoundException e = new NotFoundException("L'utente '" + utente.getUsername() + "' non possiede questo ruolo ('" + nomeRuolo + "')");
            log.warning(e.getMessage());
            throw e;
        }
        utente.getRuoli().remove(ruolo);
        utenteService.saveOrUpdate(utente);
        String okMsg = "Ruolo '" + nomeRuolo + "' rimosso all'utente '" + usernameUtente + "'.";
        log.info(okMsg);
        return new ResponseEntity<InfoMsg>(
                new InfoMsg(
                        new Date(),
                        okMsg
                ),
                HttpStatus.OK
        );
    }

    @ApiOperation(
            value = "Inserisce l'Utente fornito.",
            notes = "L'operazione va a buon fine se i dati sono tutti validi",
            response = InfoMsg.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Utente inserito!"),
            @ApiResponse(code = 400, message = "JsonObject formato in modo non corretto, seguire il modello documentato su Swagger!"),
            @ApiResponse(code = 406, message = "Binding Validation non andata a buon fine, vedi errore."),
            @ApiResponse(code = 409, message = "Utente non inserito perchè già esistente.")
    })
    @PostMapping("insert")
    @SneakyThrows
    public ResponseEntity<InfoMsg> addUtente(
            @ApiParam(value = "JsonObject descrivente l'Utente da inserire", required = true)
            @Valid
            @RequestBody(required = true)
            Utente utente,
            BindingResult result
    ) {
        log.info("Richiesto l'inserimento di un Utente");
        if (result.hasErrors()) {
            String errMsg = result.getAllErrors().get(0).getDefaultMessage();
            //if(errMsg == null) errMsg = "Errore generico nell'inserimento della Segnalazione!";
            System.err.println(errMsg);
            log.warning(errMsg);
            throw new BindingException(errMsg);
        }
        if (utenteService.findUtenteByUsername(utente.getUsername()) == null) {
            utenteService.saveOrUpdate(utente);
            String okMsg = "Inserimento completato.";
            log.info(okMsg);
            return new ResponseEntity<InfoMsg>(new InfoMsg(
                    new Date(),
                    okMsg
            ), HttpStatus.CREATED);
        } else {
            DuplicateException d = new DuplicateException("Utente " + utente.getUsername() + " esiste già.");
            log.warning(d.getMessage());
            throw d;
        }
    }
}
