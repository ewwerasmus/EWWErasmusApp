package com.tuxdave.erasmusapp.ws_login.service;

import com.tuxdave.erasmusapp.ws_login.entity.Utente;
import com.tuxdave.erasmusapp.ws_login.repository.UtenteReposotory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UtenteServiceImpl implements UtenteService {

    @Autowired
    UtenteReposotory utenteReposotory;

    @Override
    public List<Utente> findAllUtenti() {
        return utenteReposotory.findAll();
    }

    @Override
    public Utente findUtenteByUsername(String username) {
        return utenteReposotory.getById(username);
    }

    @Override
    @Transactional(readOnly = false)
    public void saveOrUpdate(Utente u) {
        utenteReposotory.save(u);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Utente u) {
        utenteReposotory.delete(u);
    }

    @Override
    public boolean checkPassword(Utente user, String passwd) {
        return true; //TODO: Implementare il controllo di comparazione tra passwd e hash
    }
}
