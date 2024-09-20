package ru.job4j.url.shortcut.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.url.shortcut.dto.LinkDto;
import ru.job4j.url.shortcut.mapper.LinkDtoMapper;
import ru.job4j.url.shortcut.model.Link;
import ru.job4j.url.shortcut.model.Site;
import ru.job4j.url.shortcut.model.Statistic;
import ru.job4j.url.shortcut.repository.LinkRepository;
import ru.job4j.url.shortcut.repository.StatisticRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleLinkServiceTest {

    private LinkRepository linkRepository;
    private StatisticRepository statisticRepository;
    private LinkDtoMapper linkDtoMapper;
    private LinkService linkService;
    private Site site;
    private LinkDto linkDto;
    private Link link;

    @BeforeEach
    public void initServices() {
        linkRepository = mock(LinkRepository.class);
        statisticRepository = mock(StatisticRepository.class);
        linkDtoMapper = mock(LinkDtoMapper.class);
        linkService = new SimpleLinkService(linkRepository, statisticRepository, linkDtoMapper);
        linkDto = new LinkDto();
        site = new Site();
        site.setId(1L);
        site.setSiteName("testName");
        site.setLogin("test@ex.com");
        site.setPassword("123t");
        linkDto.setSite(site);
        linkDto.setUrl("https://www.example.com");
        link = new Link();
        link.setId(1L);
        link.setUrl("https://www.example.com");
        link.setConvertedUrl("test11");
        link.setSite(site);
    }

    @Test
    public void whenSaveNewLinkThenFoundSavedLink() {
        when(linkRepository.save(link)).thenReturn(link);
        var newLink = linkService.save(link);
        assertThat(newLink).isEqualTo(link);
    }

    @Test
    public void whenCreateNewLinkFromLinkDtoThenFoundSavedLink() {
        when(linkDtoMapper.linkDtoToLink(linkDto)).thenReturn(link);
        when(linkRepository.save(link)).thenReturn(link);
        var newLink = linkService.create(linkDto);
        assertThat(newLink).isEqualTo(link);
    }

    @Test
    public void checkConverter() {
        var shortUrl = linkService.convertUrl(site, "https://www.example.com");
        assertThat(shortUrl.length()).isEqualTo(6);
    }
}