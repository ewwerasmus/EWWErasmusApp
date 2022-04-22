package com.tuxdave.erasmusapp.ws_login.service;

import com.tuxdave.erasmusapp.ws_login.entity.Utente;
import com.tuxdave.erasmusapp.ws_login.repository.UtenteReposotory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UtenteServiceImpl implements UtenteService {

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    UtenteReposotory utenteReposotory;

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
    public boolean checkPassword(Utente user, String passwd) {
        return encoder.matches(passwd, user.getPassword());
    }
}
