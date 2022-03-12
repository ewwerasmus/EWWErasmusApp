package com.tuxdave.erasmusapp.ws_segnalazioni.repository;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Coordinata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CoordinataRepository extends JpaRepository<Coordinata, Long> {

    @Query("SELECT C FROM Coordinata C WHERE C.latitudine = (:lat) AND C.longitudine = (:lon)")
    Coordinata findByLatitudineAndLongitudine(@Param("lat") Double lat, @Param("lon") Double lon);
}