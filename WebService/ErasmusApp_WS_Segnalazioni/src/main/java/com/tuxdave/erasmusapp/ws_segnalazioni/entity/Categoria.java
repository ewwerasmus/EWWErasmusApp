package com.tuxdave.erasmusapp.ws_segnalazioni.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "CATEGORIA")
@Getter
@Setter
@RequiredArgsConstructor
public class Categoria implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(message = "L'id di Categoria deve essere NULL in inserimento")
    @Min(value = 0, message = "L'id di Categoria deve essere minimo: 0")
    private Integer id;

    @NotNull(message = "Il nome di Categoria non può essere NULL")
    @NotBlank(message = "Il nome di Categoria non può essere BLANK")
    @Size(max = 45, message = "Il nome di Categoria deve essere compreso tra 10 e 45 chars")
    private String nome;

    @NotNull(message = "La descrizione di Segnalazione non può essere NULL")
    @NotBlank(message = "La descrizione di Segnalazione non può essere BLANK")
    @Size(min=10, max = 2000, message = "La descrizione di Segnalazione può essere lunga 10-2000 chars")
    private String descrizione;

    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<Segnalazione> segnalazioni;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Categoria categoria = (Categoria) o;
        return id != null && Objects.equals(id, categoria.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}