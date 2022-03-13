package com.tuxdave.erasmusapp.ws_segnalazioni.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "COORDINATA")
@Getter
@Setter
@RequiredArgsConstructor
public class Coordinata implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(message = "L'id di Coordinata deve essere NULL in inserimento")
    private Long id;

    @NotNull(message = "La latitudine di Coordinata non può essere NULL")
    private Double latitudine;

    @NotNull(message = "La longitudine di Coordinata non può essere NULL")
    private Double longitudine;

    @OneToMany(mappedBy = "coordinata", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<Segnalazione> segnalazioni;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Coordinata that = (Coordinata) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}