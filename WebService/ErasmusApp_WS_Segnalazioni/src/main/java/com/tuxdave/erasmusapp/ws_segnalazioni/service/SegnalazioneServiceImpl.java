package com.tuxdave.erasmusapp.ws_segnalazioni.service;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Categoria;
import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Coordinata;
import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Segnalazione;
import com.tuxdave.erasmusapp.ws_segnalazioni.repository.CategoriaRepository;
import com.tuxdave.erasmusapp.ws_segnalazioni.repository.ComuneRepository;
import com.tuxdave.erasmusapp.ws_segnalazioni.repository.CoordinataRepository;
import com.tuxdave.erasmusapp.ws_segnalazioni.repository.SegnalazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = false)
public class SegnalazioneServiceImpl implements SegnalazioneService {

    @Autowired
    SegnalazioneRepository segnalazioneRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    ComuneRepository comuneRepository;

    @Autowired
    CoordinataRepository coordinataRepository;

    @Override
    public Segnalazione findSegnalazioneById(Long id) {
        return segnalazioneRepository.findSegnalazioneById(id);
    }

    @Override
    public List<Segnalazione> searchSegnalazioneByComune_CodiceCatastale(String codiceCatastale) {
        return segnalazioneRepository.searchSegnalazioneByComune_CodiceCatastale(codiceCatastale);
    }

    @Override
    public List<Segnalazione> searchSegnalazioneByCategoria_Id(Integer id) {
        return segnalazioneRepository.searchSegnalazioneByCategoria_Id(id);
    }

    @Override
    public List<Segnalazione> searchSegnalazioneByStatoSegnalazione(Integer stato) {
        return segnalazioneRepository.searchSegnalazioneByStatoSegnalazione(stato);
    }

    @Override
    public void setStatoSegnalazione(Integer newStato, Segnalazione segnalazione) {
        segnalazioneRepository.setStatoSegnalazione(newStato, segnalazione);
    }

    @Override
    public void save(Segnalazione s) {
        Optional<Categoria> cat = categoriaRepository.findById(s.getCategoria().getId());
        if(cat.isEmpty()){
            //categoriaRepository.saveAndFlush(s.getCategoria());
        }
        //TODO: Capire se lanciare una ecc se il comune e categoria non esistono
 //TODO: risolvere la possibile nullità dell'indice
        //Fare in modo che l'inserimento vada a buon fine con comune e categoria esistenti, coodinate esistenti o da creare
        Optional<Coordinata> coor = coordinataRepository.findById(s.getCoordinata().getId() == null ? s.getCoordinata().getId() : -1L);
        if(coor.isEmpty()){
            coordinataRepository.saveAndFlush(s.getCoordinata());
        }
        segnalazioneRepository.saveAndFlush(s);
    }
}
