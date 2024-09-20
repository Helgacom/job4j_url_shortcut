package ru.job4j.url.shortcut.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.url.shortcut.dto.SiteDto;
import ru.job4j.url.shortcut.mapper.SiteDtoMapper;
import ru.job4j.url.shortcut.model.Site;
import ru.job4j.url.shortcut.repository.SiteRepository;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleSiteServiceTest {

    private SiteRepository siteRepository;
    private SiteDtoMapper siteDtoMapper;
    private SiteService siteService;
    private SiteDto siteDto;
    private Site site;


    @BeforeEach
    public void initServices() {
        siteRepository = mock(SiteRepository.class);
        siteDtoMapper = mock(SiteDtoMapper.class);
        siteService = new SimpleSiteService(siteRepository, siteDtoMapper);
        siteDto = new SiteDto();
        siteDto.setId(0L);
        siteDto.setSiteName("SiteName");
        siteDto.setLogin("test@ex.com");
        siteDto.setPassword("123t");
        site = new Site();
        site.setId(1L);
        site.setSiteName("testName");
        site.setLogin("test@ex.com");
        site.setPassword("123t");
    }

    @Test
    public void whenSaveNewSiteThenFoundSavedSite() {
        when(siteRepository.save(site)).thenReturn(site);
        var newSite = siteService.save(site);
        assertThat(newSite).isEqualTo(site);
    }

    @Test
    public void whenCreateNewSiteFromSiteDtoThenFoundSavedSite() {
        when(siteDtoMapper.siteDtoToSite(siteDto)).thenReturn(site);
        when(siteRepository.save(site)).thenReturn(site);
        var newSite = siteService.create(siteDto);
        assertThat(newSite).isEqualTo(site);
    }

    @Test
    public void whenExistByLoginThenReturnTrue() {
        when(siteRepository.existsByLogin(site.getLogin())).thenReturn(true);
        assertThat(siteService.existsByLogin("test@ex.com")).isTrue();
    }
}