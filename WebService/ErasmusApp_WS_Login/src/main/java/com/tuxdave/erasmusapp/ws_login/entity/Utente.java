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

@Entity(name = "Utente")
@Table(name = "UTENTE")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Utente {
    @Id
    @Size(min = 5, max = 80, message = "L'USERNAME di Utente deve essere di lunghezza 5-80.")
    private String username;

    //@JsonIgnore TODO: capire come non inviare la password cifrata in json...
    @ToString.Exclude
    @Size(min = 5, max = 80, message = "La PASSWORD di Utente deve essere di lunghezza 5-80.")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "UTENTE_RUOLO",
            joinColumns = @JoinColumn(name = "idUtente"),
            inverseJoinColumns = @JoinColumn(name = "idRuolo")
    )
    private List<Ruolo> ruoli;// = new ArrayList<>();
    public List<Ruolo> getRuoli() {
        if(ruoli == null) ruoli = new ArrayList<Ruolo>();
        return ruoli;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Utente utente = (Utente) o;
        return username != null && Objects.equals(username, utente.username);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
