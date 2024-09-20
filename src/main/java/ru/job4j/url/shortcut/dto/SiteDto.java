package ru.job4j.url.shortcut.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.job4j.url.shortcut.model.Link;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SiteDto {

    private Long id;

    private String login;

    private String password;

    private String siteName;

    private Set<Link> links = new HashSet<>();
}
