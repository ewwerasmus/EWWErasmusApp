package com.tuxdave.erasmusapp.ws_segnalazioni.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "SEGNALAZIONE")
@Getter
@Setter
@RequiredArgsConstructor
public class Segnalazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descrizione;
    private Integer urgenza;

    @Column(name = "idStatoSegnalazione")
    private Integer statoSegnalazione;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idComune")
    private Comune comune;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCoordinata", nullable = false)
    @JsonIgnore
    private Coordinata coordinata;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCategoria", nullable = false)
    @JsonIgnore
    private Categoria categoria;

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

    //mappatura della tabella ReadOnly ENUM_STATO_SEGNALAZIONE
    public static enum StatoSegnalazione {
        DA_RISOLVERE(0),
        IN_RISOLUZIONE(1),
        RISOLTO(2);

        private final int value;

        private StatoSegnalazione(int n){
            value = n;
        }

        public int getValue() {
            return value;
        }

        public static StatoSegnalazione of(int n){
            if(n < 0) n = 0;
            if(n > 2) n = 2;
            for(StatoSegnalazione s : StatoSegnalazione.values()){
                if(s.getValue() == n) return s;
            }
            return DA_RISOLVERE;
        }


        @Override
        public String toString() {
            return super.toString().replace("_", " ");
        }
    }
}