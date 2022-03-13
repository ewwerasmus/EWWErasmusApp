package com.tuxdave.erasmusapp.ws_segnalazioni.controller;

import com.tuxdave.erasmusapp.ws_segnalazioni.Utils;
import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Segnalazione;
import com.tuxdave.erasmusapp.ws_segnalazioni.exception.classic.SaveException;
import com.tuxdave.erasmusapp.ws_segnalazioni.exception.custom.BindingException;
import com.tuxdave.erasmusapp.ws_segnalazioni.exception.custom.NotFoundException;
import com.tuxdave.erasmusapp.ws_segnalazioni.service.SegnalazioneService;
import com.tuxdave.erasmusapp.ws_segnalazioni.validation.InfoMsg;
import io.swagger.annotations.*;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.Date;
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
            @ApiResponse(code = 200, message = "Tutte le segnalazioni trovate!"),
    })
    @GetMapping("query")
    public ResponseEntity<List<Segnalazione>> getAllSegnalazioni(){
        log.info("Richieste tutte le segnalazioni...");
        List<Segnalazione> ls = segnalazioneService.findAll();
        log.info("Rilasciata una lista di " + ls.size() + " segnalazioni!");
        return new ResponseEntity<List<Segnalazione>>(ls, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Seleziona la segnalazione richiesta in base all'id.",
            notes = "Restituisce i dati semplici della segnalazione in formato JsonArray.",
            response = Segnalazione.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Segnalazione richiesta trovata!"),
            @ApiResponse(code = 404, message = "Nessuna segnalazione trovata con l'id inserito")
    })
    @GetMapping("/query/id/{id}")
    @SneakyThrows
    public ResponseEntity<Segnalazione> getSegnalazioneById(
            @ApiParam(value = "ID univoco della Segnalazione richiesta.", required = true)
            @PathVariable("id")
            Long id
    ){
        log.info("Richiesta segnalazione id: " + id);
        Segnalazione s = segnalazioneService.findSegnalazioneById(id);
        if(s == null){
            String errMsg = "Nessuna segnalazione trovata con id: " + id;
            log.warning(errMsg);
            throw new NotFoundException(errMsg);
        }
        return new ResponseEntity<Segnalazione>(s, HttpStatus.OK);
    }

    //TODO: fare endpoint di inserimento anche per verificare funzionamento binding validation
    // catchare anche SaveException dall'handler quando si verificherà per emettere un messaggio di errore
    @PostMapping("insert")
    @SneakyThrows
    public ResponseEntity<InfoMsg> insertSegnalazione(
            @Valid
            @RequestBody
            Segnalazione segnalazione,
            BindingResult bindingResult,

            @RequestParam(value = "force", required = false, defaultValue = "false")
            Boolean forceInsert
    ) {
        log.info("Richiesto l'inserimento di una Segnalazione");
        if(bindingResult.hasErrors()){
            String errMsg = bindingResult.getFieldError().getDefaultMessage();
            if(errMsg == null) errMsg = "Errore generico nell'inserimento della Segnalazione!";
            log.warning(errMsg);
            throw new BindingException(errMsg);
        }

        //verifica della duplicazione dell'istanza in base alla stessa categoria, comune e posizione nel range di
        //0.00003 in
        try{
            String okMsg = "Inserimento completato!";
            if(forceInsert){
                segnalazioneService.save(segnalazione);
                log.info(okMsg);
                return new ResponseEntity<InfoMsg>(new InfoMsg(
                        new Date(),
                        okMsg
                ), HttpStatus.CREATED);
            }else{
                List<Segnalazione> l = segnalazioneService.searchSegnalazioneByCategoria_Id(segnalazione.getCategoria().getId());
                l = new Utils<Segnalazione>().intersecaListe(
                        l,
                        segnalazioneService.searchSegnalazioneByComune_CodiceCatastale(segnalazione.getComune().getCodiceCatastale())
                );
                l = new Utils<Segnalazione>().intersecaListe(
                        l,
                        segnalazioneService
                );
            }

        }catch (SaveException){
            //TODO: Do something
        }
    }
}
