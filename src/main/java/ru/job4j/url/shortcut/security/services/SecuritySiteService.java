package ru.job4j.url.shortcut.security.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.url.shortcut.model.Site;
import ru.job4j.url.shortcut.security.repository.RoleRepository;
import ru.job4j.url.shortcut.security.dtos.request.SignupRequestDTO;
import ru.job4j.url.shortcut.security.dtos.response.RegisterDTO;
import ru.job4j.url.shortcut.security.models.ERole;
import ru.job4j.url.shortcut.security.models.Role;
import ru.job4j.url.shortcut.repository.SiteRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class SecuritySiteService {
    private PasswordEncoder encoder;
    private final SiteRepository siteRepository;
    private final RoleRepository roleRepository;

    public RegisterDTO signUp(SignupRequestDTO signUpRequest) {
        if (Boolean.TRUE.equals(siteRepository.existsByLogin(signUpRequest.getLogin()))) {
            return new RegisterDTO(HttpStatus.BAD_REQUEST, "Error: This login is already taken!");
        }

        var site = new Site(
                signUpRequest.getLogin(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getSiteName()
        );

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        Supplier<RuntimeException> supplier = () -> new RuntimeException("Error: Role is not found.");

        if (strRoles == null) {
            roles.add(roleRepository.findByName(ERole.ROLE_USER).orElseThrow(supplier));
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> roles.add(roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(supplier));
                    default -> roles.add(roleRepository.findByName(ERole.ROLE_USER).orElseThrow(supplier));
                }
            });
        }
        site.setRoles(roles);
        siteRepository.save(site);
        return new RegisterDTO(HttpStatus.OK, "Site registered successfully!");
    }
}
