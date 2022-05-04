package com.tuxdave.erasmusapp.ws_login.service;

import com.tuxdave.erasmusapp.shared.exception.custom.NotFoundException;
import com.tuxdave.erasmusapp.ws_login.entity.Ruolo;
import com.tuxdave.erasmusapp.ws_login.entity.Utente;
import com.tuxdave.erasmusapp.ws_login.repository.RuoloRepository;
import com.tuxdave.erasmusapp.ws_login.repository.UtenteReposotory;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UtenteServiceImpl implements UtenteService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UtenteReposotory utenteReposotory;

    @Autowired
    private RuoloRepository ruoloRepository;

    @Autowired
    private RuoloService ruoloService;

    @Override
    public List<Utente> findAllUtenti() {
        return utenteReposotory.findAll();
    }

    @Override
    public Utente findUtenteByUsername(String username) {
        Optional<Utente> ut = utenteReposotory.findById(username);
        if(ut.isEmpty()) return null;
        else return ut.get();
    }

    @Override
    @Transactional(readOnly = false)
    public void saveOrUpdate(Utente u) {
        if(!u.getPassword().equals(utenteReposotory.findEncryptedPassewordByUtente(u.getUsername()))){
            u.setPassword(encoder.encode(u.getPassword()));
        }
        utenteReposotory.saveAndFlush(u);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Utente u) {
        utenteReposotory.delete(u);
    }

    @Override
    public List<Utente> searchUtenteByRuoloNome(String r) throws NotFoundException {
        if(!ruoloService.doesRuoloExist(r)){
            throw new NotFoundException("Ruolo " + r + " non trovato...");
        }
        return utenteReposotory.searchUtenteByRuoloNome(r);
    }

    @Override
    public List<Utente> searchUtenteBySyncRuoliNome(String[] ns) throws NotFoundException {
        for (String n : ns) {
            System.out.println(n);
        }
        if(ns.length == 0){
            return new ArrayList<>();
        }else if(ns.length == 1){
            return searchUtenteByRuoloNome(ns[0]);
        }else {
            List<String> nomi = new ArrayList<>(List.of(ns));
            List<Utente> us = searchUtenteByRuoloNome(nomi.get(0));
            List<Utente> ret = new ArrayList<>();
            nomi.remove(nomi.get(0));
            List<Ruolo> nomiR = ruoloRepository.findRuoliByNomi(nomi.toArray(new String[]{}));
            if(nomiR.size() != nomi.size()){
                throw new NotFoundException(Math.abs(nomi.size() - nomiR.size()) + " Ruoli non sono stati trovati.");
            }
            for(Utente u : us){
                if(u.getRuoli().containsAll(nomiR)){
                    ret.add(u);
                }
            }
            return ret;
        }
    }

    @Override
    public boolean checkPassword(Utente user, String passwd) {
        return encoder.matches(passwd, user.getPassword());
    }
}
