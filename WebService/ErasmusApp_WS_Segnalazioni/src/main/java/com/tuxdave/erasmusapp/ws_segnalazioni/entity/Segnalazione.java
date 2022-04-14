package com.tuxdave.erasmusapp.ws_segnalazioni.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tuxdave.erasmusapp.shared.validation.orders.First;
import com.tuxdave.erasmusapp.shared.validation.orders.Second;
import com.tuxdave.erasmusapp.ws_segnalazioni.validation.V_Categoria;
import com.tuxdave.erasmusapp.ws_segnalazioni.validation.V_Comune;
import com.tuxdave.erasmusapp.ws_segnalazioni.validation.V_StatoSegnalazione;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.GroupSequence;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "SEGNALAZIONE")
@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@GroupSequence({Segnalazione.class, First.class, Second.class}) //ordine di validazione
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idComune")
    @NotNull(message = "Il Comune di Segnalazione non può essere NULL", groups = First.class)
    @V_Comune(groups = Second.class)
    private Comune comune;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idCoordinata", nullable = false)
    @NotNull(message = "Le Coordinate di Segnalazione non possono essere NULL")
    // @V_Coordinata
    private Coordinata coordinata;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idCategoria", nullable = false)
    @JsonAlias("Wewe")
    @NotNull(message = "La Categodia di Segnalazione non può essere NULL", groups = First.class)
    @V_Categoria(groups = Second.class)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idStatoSegnalazione")
    @NotNull(message = "Lo StatoSegnalazione di Segnalazione non può essere NULL", groups = First.class)
    @V_StatoSegnalazione(groups = Second.class)
    private StatoSegnalazione statoSegnalazione;

    public Segnalazione(Long id, String descrizione, Integer urgenza, StatoSegnalazione statoSegnalazione) {
        this.id = id;
        this.descrizione = descrizione;
        this.urgenza = urgenza;
        this.statoSegnalazione = statoSegnalazione;
    }

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