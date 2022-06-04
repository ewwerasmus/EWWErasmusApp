package com.tuxdave.erasmusapp.ws_segnalazioni.security;

import com.tuxdave.erasmusapp.ws_segnalazioni.security.entity.Utente;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import net.bytebuddy.build.BuildLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.swing.plaf.ButtonUI;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
@Log
@Qualifier("CustomUserDetailsService")
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserConfig userConfig;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String errMsg = "";
        if(username == null || username.length() < 5){
            errMsg = "Nome utente assente o non valido.";
            log.warning(errMsg);
            throw new UsernameNotFoundException(errMsg);
        }else{
            Utente utente = getHttpValue(username);
            if(utente == null){
                UsernameNotFoundException u = new UsernameNotFoundException(String.format("Username %s non trovato!", username));
                log.warning(u.getMessage());
                throw u;
            }else{
                System.out.println(utente.getUsername());
                for (String s : utente.getRuoli()
                        .stream().map(a -> "ROLE_" + a.getNome())
                        .toArray(String[]::new)) {
                    System.out.println(s);
                }
                User.UserBuilder builder = User.withUsername(utente.getUsername());
                builder.password(utente.getPassword());
                builder.authorities(
                        utente.getRuoli()
                                .stream().map(a -> "ROLE_" + a.getNome())
                                .toArray(String[]::new)
                );
                return builder.build();
            }
        }
    }

    @SneakyThrows
    private Utente getHttpValue(String username) {
        URI url = new URI(userConfig.getServerUrl() + username);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors()
                .add(new BasicAuthenticationInterceptor(userConfig.getUsername(), userConfig.getPassword()));
        Utente utente = null;
        try{
            utente = restTemplate.getForObject(url, Utente.class);
        }catch (Exception e){
            log.warning("Connessione al WS_Login non riuscita!");
            throw e;
        }
        return utente;
    }
}
