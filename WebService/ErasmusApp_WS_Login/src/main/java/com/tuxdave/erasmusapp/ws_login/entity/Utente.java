package com.tuxdave.erasmusapp.ws_login.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "UTENTE")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Utente {
    @Id
    @Size(min = 5, max = 80, message = "L'USERNAME di Utente deve essere di lunghezza 5-80.")
    private String username;

    @Size(min = 5, max = 80, message = "La PASSWORD di Utente deve essere di lunghezza 5-80.")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "UTENTE_RUOLO",
            joinColumns = @JoinColumn(name = "idUtente"),
            inverseJoinColumns = @JoinColumn(name = "idRuolo")
    )
    @Null(message = "L'elenco di RUOLI deve essere NULL in inserimento.")
    private List<Ruolo> ruoli;

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
