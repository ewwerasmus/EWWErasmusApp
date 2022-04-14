package com.tuxdave.erasmusapp.ws_segnalazioni.controller;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Categoria;
import com.tuxdave.erasmusapp.shared.exception.custom.BindingException;
import com.tuxdave.erasmusapp.shared.exception.custom.DuplicateException;
import com.tuxdave.erasmusapp.shared.exception.custom.NotFoundException;
import com.tuxdave.erasmusapp.ws_segnalazioni.service.CategoriaService;
import com.tuxdave.erasmusapp.shared.validation.InfoMsg;
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
@RequestMapping("/api/categoria")
@Log
@Api(value = "Categoria Controller", tags = "Controller di gestione di tutte le categorie di Segnalazione e dati ad esse dipendenti.")
public class CategoriaController {
    @Autowired
    CategoriaService categoriaService;

    @ApiOperation(
            value = "Seleziona tutte le categorie.",
            notes = "Restituisce i dati semplici delle categorie in formato JsonArray.",
            response = List.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tutte le categorie trovate!"),
    })
    @GetMapping("query")
    public ResponseEntity<List<Categoria>> getAllCategorie(){
        log.info("Richieste tutte le categorie.");
        List<Categoria> categorie = categoriaService.findAll();
        log.info("Rilasciate " + categorie.size() + " categorie.");
        return new ResponseEntity<List<Categoria>>(categorie, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Seleziona una categoria in base all'ID.",
            notes = "Restituisce i dati semplici delle categorie in formato JsonObject.",
            response = Categoria.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "La categoria richiesta è stata trovata!"),
            @ApiResponse(code = 404, message = "La categoria richiesta non è stata trovata..."),
    })
    @GetMapping("query/id/{id}")
    public ResponseEntity<Categoria> getCategoriaById(
            @ApiParam(required = true, value = "L'ID della categoria da selezionare")
            @PathVariable(value = "id", required = true)
            Integer id
    ) throws NotFoundException {
        log.info("Richiesta la categoria con ID = " + id);
        Categoria categoria = categoriaService.findCategoriaById(id);
        if(categoria == null){
            String err = "La categoria con ID = " + id + " non esiste...";
            log.warning(err);
            throw new NotFoundException(err);
        }else{
            log.info("Rispondo con la categoria '" + categoria.getNome() + "'");
            return new ResponseEntity<Categoria>(categoria, HttpStatus.OK);
        }
    }

    @ApiOperation(
            value = "Inserisce la categoria fornita.",
            notes = "L'operazione va a buon fine se il NOME non è uguale ad un'altra categoria esistente.",
            response = Categoria.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Categoria inserita!"),
            @ApiResponse(code = 400, message = "JsonObject formato in modo non corretto, seguire il modello documentato su Swagger!"),
            @ApiResponse(code = 409, message = "Categoria non inserita perchè già esistente!")
    })
    @PostMapping("insert")
    @SneakyThrows
    public ResponseEntity<InfoMsg> insertSegnalazione(
            @ApiParam(value = "JsonObject descrivente la Categoria da inserire", required = true)
            @Valid
            @RequestBody(required = true)
            Categoria categoria,
            BindingResult bindingResult
    ) {
        log.info("Richiesto l'inserimento di una Categoria");
        if(bindingResult.hasErrors()){
            String errMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
            //if(errMsg == null) errMsg = "Errore generico nell'inserimento della Segnalazione!";
            System.err.println(errMsg);
            log.warning(errMsg);
            log.warning("Inserimento rifiutato!");
            throw new BindingException(errMsg);
        }
        String okMsg = "Inserimento completato!";

        //controllo di non duplicazione
        Categoria c = categoriaService.findCategoriaByNome(categoria.getNome());
        if(c != null){
            String errMsg = "Inserimento rifiutato: categoria già presente!";
            log.warning(errMsg);
            throw new DuplicateException(errMsg);
        }

        categoriaService.save(categoria);
        log.info(okMsg);
        return new ResponseEntity<InfoMsg>(new InfoMsg(
                new Date(),
                okMsg
        ), HttpStatus.CREATED);
    }
}
