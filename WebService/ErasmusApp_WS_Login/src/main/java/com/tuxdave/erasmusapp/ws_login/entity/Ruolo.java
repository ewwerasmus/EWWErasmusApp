package com.tuxdave.erasmusapp.ws_login.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "RUOLO")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Ruolo {
    @Id
    @Size(min = 3, max = 50, message = "Il NOME del Ruolo deve essere di lunghezza 3-50.")
    private String nome;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "ruoli")
    @JsonIgnore
    @ToString.Exclude
    @Null(message = "L'elenco di UTENTI deve essere NULL in inserimento.")
    private List<Utente> utenti;// = new ArrayList<>();
    public List<Utente> getUtenti() {
        if(utenti == null) utenti = new ArrayList<Utente>();
        return utenti;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ruolo ruolo = (Ruolo) o;
        return nome != null && Objects.equals(nome, ruolo.nome);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
