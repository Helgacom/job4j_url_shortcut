package ru.job4j.url.shortcut.security.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDTO {

    private String siteName;

    private String login;

    private Set<String> role;

    private String password;
}
