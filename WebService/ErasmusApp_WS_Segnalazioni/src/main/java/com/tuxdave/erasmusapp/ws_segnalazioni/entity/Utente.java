package com.tuxdave.erasmusapp.ws_segnalazioni.entity;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "UTENTE_COMUNE",
            joinColumns = @JoinColumn(name = "idUtente"),
            inverseJoinColumns = @JoinColumn(name = "idComune")
    )
    @Null(message = "L'Utente deve avere i Ruoli NULLI in inserimento.")
    private List<Comune> comuni;// = new ArrayList<>();
    public List<Comune> getComuni() {
        if(comuni == null) comuni = new ArrayList<Comune>();
        return comuni;
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
