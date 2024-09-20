package ru.job4j.url.shortcut.security.dtos.response;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponseDTO {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String siteName;
    private String login;
    private List<String> roles;

    public JwtResponseDTO(String accessToken, Long id, String siteName, String login, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.siteName = siteName;
        this.login = login;
        this.roles = roles;
    }
}
