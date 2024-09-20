package ru.job4j.url.shortcut.service;

import ru.job4j.url.shortcut.dto.SiteDto;
import ru.job4j.url.shortcut.model.Site;

import java.util.List;
import java.util.Optional;

public interface SiteService {

    Site save(Site site);

    Site create(SiteDto siteDto);

    boolean update(SiteDto siteDto);

    Optional<Site> findById(Long id);

    void deleteById(Long id);

    List<Site> findAll();

    boolean existsByLogin(String login);

    Optional<Site> findByLogin(String login);

    Optional<Site> findByLoginAndPassword(String login, String password);
}
