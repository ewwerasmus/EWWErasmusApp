package com.tuxdave.erasmusapp.ws_segnalazioni.exception;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMsg
{
    private Date date;
    private int code;
    private String message;
}
