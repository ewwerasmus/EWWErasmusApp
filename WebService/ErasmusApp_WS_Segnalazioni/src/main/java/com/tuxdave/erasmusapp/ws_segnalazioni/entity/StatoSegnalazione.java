package com.tuxdave.erasmusapp.ws_segnalazioni.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ENUM_STATO_SEGNALAZIONE")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StatoSegnalazione {
    @Id
    private Integer id;
    private String label;

    @OneToMany(mappedBy = "statoSegnalazione", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private Set<Segnalazione> segnalaziones = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StatoSegnalazione that = (StatoSegnalazione) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}