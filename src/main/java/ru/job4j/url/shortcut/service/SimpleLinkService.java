package ru.job4j.url.shortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.url.shortcut.dto.LinkDto;
import ru.job4j.url.shortcut.mapper.LinkDtoMapper;
import ru.job4j.url.shortcut.model.Link;
import ru.job4j.url.shortcut.model.Site;
import ru.job4j.url.shortcut.repository.LinkRepository;
import ru.job4j.url.shortcut.repository.StatisticRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class SimpleLinkService implements LinkService {

    private final LinkRepository linkRepository;
    private final StatisticRepository statisticRepository;
    private final LinkDtoMapper linkDtoMapper;

    @Override
    public Link save(Link link) {
        return linkRepository.save(link);
    }

    @Override
    public Link create(LinkDto linkDto) {
        var link = linkDtoMapper.linkDtoToLink(linkDto);
        link.setConvertedUrl(createShortUrl());
        return linkRepository.save(link);
    }

    @Override
    public boolean update(Link link) {
        return linkRepository.update(link) > 0;
    }

    @Override
    public Optional<Link> findById(Long id) {
        return linkRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        linkRepository.deleteById(id);
    }

    @Override
    public List<Link> findAll() {
        return linkRepository.findAll();
    }

    @Override
    public List<Link> findSiteLinks(Long siteId) {
        return linkRepository.findLinksBySiteId(siteId).stream().toList();
    }

    @Override
    public String getOriginalUrl(String convertedUrl) {
        var link = linkRepository.findByConvertedUrl(convertedUrl)
                .orElseThrow(() -> new NoSuchElementException("This url not found"));
        statisticRepository.incrementCallCount(link.getId());
        return link.getUrl();
    }

    @Override
    public String convertUrl(Site site, String url) {
        var link = linkRepository.findByUrl(url);
        if (link.isPresent()) {
            return link.get().getConvertedUrl();
        }
        String convertedUrl;
        do {
            convertedUrl = createShortUrl();
        } while (linkRepository.findByConvertedUrl(convertedUrl).isPresent());
        var newLink = new Link();
        newLink.setUrl(url);
        newLink.setConvertedUrl(convertedUrl);
        newLink.setSite(site);
        linkRepository.save(newLink);
        statisticRepository.incrementCallCount(newLink.getId());
        return convertedUrl;
    }

    private String createShortUrl() {
        var convertedUrlLength = 6;
        var random = new Random();
        var shortUrl = new StringBuilder(convertedUrlLength);
        var charList = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnoprstuvwxyz0123456789";
        for (int i = 0; i < convertedUrlLength; i++) {
            shortUrl.append(charList.charAt(random.nextInt(charList.length())));
        }
        return shortUrl.toString();
    }
}
