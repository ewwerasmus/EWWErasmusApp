package com.tuxdave.erasmusapp.ws_segnalazioni.security;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Log
public class AuthEntryPoint extends BasicAuthenticationEntryPoint {
    public static final String REALM = "REAME1";

    @Override
    @SneakyThrows
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) {
        String errMsg = "UserID e/o Password non corretti!";
        log.warning(errMsg);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader("WWW-Authenticate","Basic realm=" + getRealmName() + "");

        PrintWriter writer = response.getWriter();
        writer.println(errMsg);
    }

    @Override
    @SneakyThrows
    public void afterPropertiesSet() {
        setRealmName(REALM);
        super.afterPropertiesSet();
    }
}
