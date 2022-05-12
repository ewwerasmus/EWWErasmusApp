package com.tuxdave.erasmusapp.ws_login.controller;

import com.tuxdave.erasmusapp.shared.exception.custom.BindingException;
import com.tuxdave.erasmusapp.shared.exception.custom.DuplicateException;
import com.tuxdave.erasmusapp.shared.exception.custom.NotFoundException;
import com.tuxdave.erasmusapp.shared.validation.InfoMsg;
import com.tuxdave.erasmusapp.ws_login.entity.Ruolo;
import com.tuxdave.erasmusapp.ws_login.entity.Utente;
import com.tuxdave.erasmusapp.ws_login.service.RuoloService;
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

    @ApiOperation(
            value = "Inserisce il Ruolo fornito.",
            notes = "L'operazione va a buon fine se i dati sono tutti validi",
            response = Ruolo.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Ruolo inserito!"),
            @ApiResponse(code = 400, message = "JsonObject formato in modo non corretto, seguire il modello documentato su Swagger!"),
            @ApiResponse(code = 406, message = "Binding Validation non andata a buon fine, vedi errore."),
            @ApiResponse(code = 409, message = "Ruolo non inserito perchè già esistente.")
    })
    @PostMapping("insert")
    @SneakyThrows
    public ResponseEntity<InfoMsg> addRuolo(
            @ApiParam(value = "JsonObject descrivente il Ruolo da inserire", required = true)
            @Valid
            @RequestBody(required = true)
            Ruolo ruolo,
            BindingResult result
    ){
        log.info("Richiesto l'inserimento di un Ruolo");
        if(result.hasErrors()) {
            String errMsg = result.getAllErrors().get(0).getDefaultMessage();
            //if(errMsg == null) errMsg = "Errore generico nell'inserimento della Segnalazione!";
            System.err.println(errMsg);
            log.warning(errMsg);
            throw new BindingException(errMsg);
        }
        if(ruoloService.findRuoloByNome(ruolo.getNome()) == null){
            ruoloService.saveOrUpdate(ruolo);
            String okMsg = "Inserimento completato.";
            log.info(okMsg);
            return new ResponseEntity<InfoMsg>(new InfoMsg(
                    new Date(),
                    okMsg
            ), HttpStatus.CREATED);
        } else {
            DuplicateException d = new DuplicateException("Ruolo " + ruolo.getNome() + " esiste già.");
            log.warning(d.getMessage());
            throw d;
        }
    }

    @ApiOperation(
            value = "Rimuove un Ruolo.",
            notes = "L'operazione va a buon fine se i dati sono tutti validi",
            response = InfoMsg.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Ruolo cancellato!"),
            @ApiResponse(code = 404, message = "Ruolo non trovati."),
    })
    @DeleteMapping("/query/nome/{nome}")
    @SneakyThrows
    public ResponseEntity<InfoMsg> deleteUtente(
            @ApiParam(value = "Il nome del Ruolo da cancellare.", required = true)
            @PathVariable("nome")
            String nome
    ){
        log.info("Richiesta la cancellazione del Ruolo '" + nome + "'");
        Ruolo ruolo = ruoloService.findRuoloByNome(nome);
        if(ruolo == null){
            NotFoundException e = new NotFoundException("Il Ruolo '" + nome + "' non esiste.");
            log.warning(e.getMessage());
            throw e;
        }
        ruoloService.delete(ruolo);
        String okMsg = "Ruolo '" + nome + "' cancellato correttamente";
        log.info(okMsg);
        return new ResponseEntity<InfoMsg>(
                new InfoMsg(
                        new Date(),
                        okMsg
                ),
                HttpStatus.CREATED
        );
    }
}
