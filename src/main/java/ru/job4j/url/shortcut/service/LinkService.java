package ru.job4j.url.shortcut.service;

import ru.job4j.url.shortcut.dto.LinkDto;
import ru.job4j.url.shortcut.model.Link;
import ru.job4j.url.shortcut.model.Site;

import java.util.List;
import java.util.Optional;

public interface LinkService {

    Link save(Link link);

    Link create(LinkDto linkDto);

    Optional<Link> findById(Long id);

    void deleteById(Long id);

    List<Link> findAll();

    List<Link> findSiteLinks(Long siteId);

    boolean update(Link link);

    String getOriginalUrl(String convertedUrl);

    String convertUrl(Site site, String url);


}
