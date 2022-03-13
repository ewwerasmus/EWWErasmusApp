package com.tuxdave.erasmusapp.ws_segnalazioni.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InfoMsg {
    private Date data;
    private String message;
}
