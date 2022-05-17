package com.tuxdave.erasmusapp.erasmusapp_ws_authjwt.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(value = "ws-login")
@Data
public class UserConfig {
    private String serverUrl;
    private String username;
    private String password;
}
