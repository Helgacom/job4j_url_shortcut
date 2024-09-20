package ru.job4j.url.shortcut.model;

import jakarta.persistence.*;
import lombok.*;
import ru.job4j.url.shortcut.security.models.Role;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sites")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@ToString
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String login;

    private String password;

    private String siteName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sites_roles", joinColumns = @JoinColumn(name = "site_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Site(String login, String password, String siteName) {
        this.login = login;
        this.password = password;
        this.siteName = siteName;
    }
}
