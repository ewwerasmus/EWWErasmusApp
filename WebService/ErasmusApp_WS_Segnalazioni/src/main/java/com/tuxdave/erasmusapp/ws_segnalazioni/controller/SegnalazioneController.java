package com.tuxdave.erasmusapp.ws_segnalazioni.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tuxdave.erasmusapp.ws_segnalazioni.Utils;
import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Segnalazione;
import com.tuxdave.erasmusapp.ws_segnalazioni.exception.classic.SaveException;
import com.tuxdave.erasmusapp.ws_segnalazioni.exception.custom.BindingException;
import com.tuxdave.erasmusapp.ws_segnalazioni.exception.custom.DuplicateException;
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
        List<Segnalazione> ls = segnalazioneService.findAllEssential();
        log.info("Rilasciata una lista di " + ls.size() + " segnalazioni!");
        return new ResponseEntity<List<Segnalazione>>(ls, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Ricerca le segnalazioni in base al comune di appartenenza.",
            notes = "Restituisce i dati semplici della segnalazione in formato JsonArray.",
            response = List.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Rilasciate le segnalazioni ricercate!"),
    })
    @GetMapping("query/search/comune/{codiceCatastale}")
    public ResponseEntity<List<Segnalazione>> searchSegnalazioneEssentialByComune(
            @ApiParam(value = "Codice Catastale del comune secondo cui la ricerca va fatta.", required = true)
            @PathVariable("codiceCatastale")
            String cc
    ){
        log.info("Ricerca delle Segnalazioni con codiceCatastale = " + cc);
        List<Segnalazione> ret = segnalazioneService.searchSegnalazioneByComune_CodiceCatastale(cc);
        log.info("Rilasciata una lista di " + ret.size() + " Segnalazioni!");
        return new ResponseEntity<List<Segnalazione>>(ret, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Ricerca le segnalazioni in base alla categoria di appartenenza.",
            notes = "Restituisce i dati semplici della segnalazione in formato JsonArray.",
            response = List.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Rilasciate le segnalazioni ricercate!"),
    })
    @GetMapping("query/categoria/{id}")
    public ResponseEntity<List<Segnalazione>> searchSegnalazioneEssentialByCategoria(
            @ApiParam(value = "ID univoco della Categoria secondo cui ricercare.", required = true)
            @PathVariable("id")
            Integer id
    ){
        log.info("Ricerca delle Segnalazioni con Id Categoria = " + id);
        List<Segnalazione> ret = segnalazioneService.searchSegnalazioneByCategoria_Id(id);
        log.info("Rilasciata una lista di " + ret.size() + " Segnalazioni!");
        return new ResponseEntity<List<Segnalazione>>(ret, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Ricerca le segnalazioni in base alla descrizione.",
            notes = "Restituisce i dati semplici della segnalazione in formato JsonArray.",
            response = List.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Rilasciate le segnalazioni ricercate!"),
    })
    @GetMapping("query/categoria/{id}")
    public ResponseEntity<List<Segnalazione>> searchSegnalazioneEssentialsByDescrizioneLike(
            @ApiParam(value = "caratteri da ricercare all'interno della descrizione.", required = true)
            @PathVariable("txt")
            String txt
    ){
        log.info("Ricerca delle Segnalazioni descrizione LIKE " + txt);
        List<Segnalazione> ret = segnalazioneService.searchSegnalazioneEssentialsByDescrizioneLike(txt);
        log.info("Rilasciata una lista di " + ret.size() + " Segnalazioni!");
        return new ResponseEntity<List<Segnalazione>>(ret, HttpStatus.OK);
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

    @ApiOperation(
            value = "Inserisce la segnalazione fornita.",
            notes = "L'operazione va a buon fine se i cambi COMUNE e CATEGORIA esistono, le coordinate vengono inserite se non esistenti",
            response = Segnalazione.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Segnalazione inserita!"),
            @ApiResponse(code = 400, message = "JsonObject formato in modo non corretto, seguire il modello documentato su Swagger!"),
            @ApiResponse(code = 406, message = "Segnalazione non inserita: COMUNE o CATEGORIA referenziano il nulla cosmico!"),
            @ApiResponse(code = 409, message = "Segnalazione non inserita perchè probabilmente già esistente, è possibile forzare l'operazione")
    })
    @PostMapping("insert")
    @SneakyThrows
    public ResponseEntity<InfoMsg> insertSegnalazione(
            @ApiParam(value = "JsonObject descrivente la Segnalazione da inserire", required = true)
            @Valid
            @RequestBody(required = true)
            Segnalazione segnalazione,
            BindingResult bindingResult,

            @RequestParam(value = "force", required = false, defaultValue = "false")
            Boolean forceInsert
    ) {
        log.info("Richiesto l'inserimento di una Segnalazione");
        if(bindingResult.hasErrors()){
            String errMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
            //if(errMsg == null) errMsg = "Errore generico nell'inserimento della Segnalazione!";
            System.err.println(errMsg);
            log.warning(errMsg);
            throw new BindingException(errMsg);
        }

        //verifica della duplicazione dell'istanza in base alla stessa categoria, comune e posizione nel range di
        //0.00003 in
        String okMsg = "Inserimento completato!";
        if(!forceInsert){
            List<Segnalazione> l = segnalazioneService.searchSegnalazioneByCategoria_Id(segnalazione.getCategoria().getId());
            l = new Utils<Segnalazione>().intersecaListe(
                    l,
                    segnalazioneService.searchSegnalazioneByComune_CodiceCatastale(segnalazione.getComune().getCodiceCatastale())
            );
            l = new Utils<Segnalazione>().intersecaListe(
                    l,
                    segnalazioneService.searchSegnalazioneByCoordinateAround(
                            segnalazione.getCoordinata().getLatitudine(),
                            segnalazione.getCoordinata().getLongitudine()
                    )
            );
            if(l.size() != 0){
                String errMsg = "Inserimento rifiutato: segnalazione probabilemnte gia presente, abilitare " +
                        "l'opzione FORCE per forzare l'inserimento!";
                log.warning(errMsg);
                throw new DuplicateException(errMsg);
            }
        }
        segnalazioneService.save(segnalazione);
        log.info(okMsg);
        return new ResponseEntity<InfoMsg>(new InfoMsg(
                new Date(),
                okMsg
        ), HttpStatus.CREATED);
    }
}
