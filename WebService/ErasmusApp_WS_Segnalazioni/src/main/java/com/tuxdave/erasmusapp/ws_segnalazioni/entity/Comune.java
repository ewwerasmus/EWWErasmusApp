package com.tuxdave.erasmusapp.ws_segnalazioni.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "COMUNE")
@Getter
@Setter
@RequiredArgsConstructor
public class Comune implements Serializable {
    @Id
    @NotNull(message = "L'id di Comune non può essere NULL")
    private String codiceCatastale;
    private String nome;
    private String provincia;

    @OneToMany(mappedBy = "comune", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<Segnalazione> segnalazioni;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "comuni")
    @JsonIgnore
    @ToString.Exclude
    @Setter
    private List<Utente> utenti;
    public List<Utente> getUtenti() {
        if(utenti == null) utenti = new ArrayList<Utente>();
        return utenti;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Comune comune = (Comune) o;
        return codiceCatastale != null && Objects.equals(codiceCatastale, comune.codiceCatastale);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}