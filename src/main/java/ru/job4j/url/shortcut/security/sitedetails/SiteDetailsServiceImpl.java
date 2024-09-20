package ru.job4j.url.shortcut.security.sitedetails;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.url.shortcut.repository.SiteRepository;


@Service
@AllArgsConstructor
public class SiteDetailsServiceImpl implements UserDetailsService {

    SiteRepository siteRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String siteName) throws UsernameNotFoundException {
        var user = siteRepository.findBySiteName(siteName)
                .orElseThrow(() -> new UsernameNotFoundException("Site Not Found with name: " + siteName));
        return SiteDetailsImpl.build(user);
    }
}
