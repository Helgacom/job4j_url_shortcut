package ru.job4j.url.shortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.url.shortcut.dto.SiteDto;
import ru.job4j.url.shortcut.mapper.SiteDtoMapper;
import ru.job4j.url.shortcut.model.Site;
import ru.job4j.url.shortcut.repository.SiteRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleSiteService implements SiteService {

    private final SiteRepository siteRepository;
    private final SiteDtoMapper siteDtoMapper;

    @Override
    public Site save(Site site) {
        return siteRepository.save(site);
    }

    @Override
    public Site create(SiteDto siteDto) {
        var site = siteDtoMapper.siteDtoToSite(siteDto);
        return siteRepository.save(site);
    }

    @Override
    public boolean update(SiteDto siteDto) {
        var site = siteDtoMapper.siteDtoToSite(siteDto);
        return siteRepository.update(site) > 0;
    }

    @Override
    public Optional<Site> findById(Long id) {
        return siteRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        siteRepository.deleteById(id);
    }

    @Override
    public List<Site> findAll() {
        return siteRepository.findAll();
    }

    @Override
    public boolean existsByLogin(String login) {
        return siteRepository.existsByLogin(login);
    }

    @Override
    public Optional<Site> findByLogin(String login) {
        return siteRepository.findByLogin(login);
    }

    @Override
    public Optional<Site> findByLoginAndPassword(String login, String password) {
        return siteRepository.findByLoginAndPassword(login, password);
    }
}
