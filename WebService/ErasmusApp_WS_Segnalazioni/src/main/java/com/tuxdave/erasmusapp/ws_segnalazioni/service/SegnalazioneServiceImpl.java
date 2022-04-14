package com.tuxdave.erasmusapp.ws_segnalazioni.service;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.*;
import com.tuxdave.erasmusapp.shared.exception.classic.SaveException;
import com.tuxdave.erasmusapp.ws_segnalazioni.repository.CategoriaRepository;
import com.tuxdave.erasmusapp.ws_segnalazioni.repository.ComuneRepository;
import com.tuxdave.erasmusapp.ws_segnalazioni.repository.CoordinataRepository;
import com.tuxdave.erasmusapp.ws_segnalazioni.repository.SegnalazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
    public List<Segnalazione> findAll() {
        return segnalazioneRepository.findAll();
    }

    @Override
    public List<Segnalazione> findAllEssential() {
        return segnalazioneRepository.findAllEssential();
    }

    @Override
    public Segnalazione findSegnalazioneById(Long id) {
        return segnalazioneRepository.findSegnalazioneById(id);
    }

    @Override
    public Segnalazione findSegnalazioneEssentialById(Long id) {
        return segnalazioneRepository.findSegnalazioneEssentialById(id);
    }

    @Override
    public List<Segnalazione> searchSegnalazioneByComune_CodiceCatastale(String codiceCatastale) {
        return segnalazioneRepository.searchSegnalazioneEssentialByComune_CodiceCatastale(codiceCatastale);
    }

    @Override
    public List<Segnalazione> searchSegnalazioneByCategoria_Id(Integer id) {
        return segnalazioneRepository.searchSegnalazioneEssentialByCategoria_Id(id);
    }

    @Override
    public List<Segnalazione> searchSegnalazioneByStatoSegnalazione(StatoSegnalazione stato) {
        return segnalazioneRepository.searchSegnalazioneEssentialByStatoSegnalazione(stato);
    }

    @Override
    public List<Segnalazione> searchSegnalazioneByCoordinateAround(Double lat, Double lon) {
        return segnalazioneRepository.searchSegnalazioneByCoordinataAround(lat, lon, AROUND_0003);
    }

    @Override
    public List<Segnalazione> searchSegnalazioneByDescrizioneLike(String descrizione) {
        return segnalazioneRepository.searchSegnalazioneEssentialByDescrizioneLike(descrizione);
    }

    @Override
    public List<Segnalazione> searchSegnalazioneByUrgenza(Integer urg) {
        return segnalazioneRepository.searchSegnalazioneEssentialByUrgenza(urg);
    }

    @Override
    public List<Segnalazione> searchSegnalazioneByUrgenzaBetween(Integer from, Integer to) {
        return segnalazioneRepository.searchSegnalazioneEssentialByUrgenzaBetween(from, to);
    }

    @Override
    public void setStatoSegnalazione(StatoSegnalazione newStato, Segnalazione segnalazione) {
        segnalazioneRepository.setStatoSegnalazione(newStato, segnalazione);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(Segnalazione s) throws SaveException {
        Optional<Categoria> cat = categoriaRepository.findById(s.getCategoria().getId());
        if(cat.isEmpty()){
            throw new SaveException("Categoria " + s.getCategoria().getId() + " non trovata!");
        }

        Optional<Comune> com = comuneRepository.findById(s.getComune().getCodiceCatastale());
        if(com.isEmpty()){
            throw new SaveException("Comune " + s.getComune().getCodiceCatastale() + " non trovato!");
        }

        Coordinata tempCoord = s.getCoordinata();
        Optional<Coordinata> coor = Optional.ofNullable(coordinataRepository.findByLatitudineAndLongitudine(
                tempCoord.getLatitudine(),
                tempCoord.getLongitudine()
        ));
        if(coor.isEmpty()){
            tempCoord.setId(null);
            coordinataRepository.saveAndFlush(tempCoord);
        }else{
            s.setCoordinata(coor.get());
        }
        System.out.println(s.getStatoSegnalazione().getId());
        segnalazioneRepository.saveAndFlush(s);
        System.out.println("saved");

    }
}
