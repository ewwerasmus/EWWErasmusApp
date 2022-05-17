package com.tuxdave.erasmusapp.erasmusapp_ws_authjwt.security.entity;

import lombok.Data;

import java.util.List;

@Data
public class Utente {
    private String username;
    private String password;
    private List<Ruolo> ruoli;
}
