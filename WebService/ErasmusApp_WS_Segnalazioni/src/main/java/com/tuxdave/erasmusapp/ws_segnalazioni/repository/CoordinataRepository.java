package com.tuxdave.erasmusapp.ws_segnalazioni.repository;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Coordinata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordinataRepository extends JpaRepository<Coordinata, Long> {
}