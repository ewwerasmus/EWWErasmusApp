package com.tuxdave.erasmusapp.ws_segnalazioni.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "SEGNALAZIONE")
@Getter
@Setter
@RequiredArgsConstructor
public class Segnalazione implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(message = "L'id di Segnalazione deve essere nullo in inserimento")
    private Long id;

    @NotNull(message = "La descrizione di Segnalazione non può essere NULL")
    @NotBlank(message = "La descrizione di Segnalazione non può essere BLANK")
    @Size(min=1, max = 2000, message = "La descrizione di Segnalazione può essere lunga al massimo 2000 chars")
    private String descrizione;

    @NotNull(message = "L'urgenza di Segnalazione non può essere NULL")
    @Max(value = 10, message = "L'urgenza di Segnalazione può avere come valore massimo: 10")
    @Min(value = 1, message = "L'urgenza di Segnalazione può avere come valore minimo: 1")
    private Integer urgenza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idComune")
    @JsonIgnore
    @NotNull(message = "Il Comune di Segnalazione non può essere NULL")
    private Comune comune;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCoordinata", nullable = false)
    @JsonIgnore
    @NotNull(message = "Le Coordinate di Segnalazione non possono essere NULL")
    private Coordinata coordinata;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCategoria", nullable = false)
    @JsonIgnore
    @NotNull(message = "La Categodia di Segnalazione non può essere NULL")
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idStatoSegnalazione")
    @NotNull(message = "Lo StatoSegnalazione di Segnalazione non può essere NULL")
    private StatoSegnalazione statoSegnalazione;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Segnalazione that = (Segnalazione) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}