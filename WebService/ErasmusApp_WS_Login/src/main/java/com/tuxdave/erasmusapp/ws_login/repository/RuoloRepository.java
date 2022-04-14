package com.tuxdave.erasmusapp.ws_login.repository;

import com.tuxdave.erasmusapp.ws_login.entity.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, String> {
}
