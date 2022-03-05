package com.tuxdave.erasmusapp.ws_segnalazioni.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "COMUNE")
@Getter
@Setter
@RequiredArgsConstructor
public class Comune {
    @Id
    private String codiceCatastale;
    private String nome;
    private String provincia;

    @OneToMany(mappedBy = "comune", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<Segnalazione> segnalazioni;

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