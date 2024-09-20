package ru.job4j.url.shortcut.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.url.shortcut.model.Link;
import ru.job4j.url.shortcut.model.Site;
import ru.job4j.url.shortcut.model.Statistic;
import ru.job4j.url.shortcut.repository.LinkRepository;
import ru.job4j.url.shortcut.repository.SiteRepository;
import ru.job4j.url.shortcut.repository.StatisticRepository;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SimpleStatisticServiceTest {

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private StatisticService statisticService;

    @BeforeEach
    public void setUp() {
        statisticRepository.deleteAll();
        linkRepository.deleteAll();
        siteRepository.deleteAll();
        statisticService = new SimpleStatisticService(statisticRepository, linkRepository);
    }

    @AfterAll
    public void clearAll() {
        statisticRepository.deleteAll();
        linkRepository.deleteAll();
        siteRepository.deleteAll();
    }

    @Test
    public void testGetStatistics() {
        var site = new Site();
        site.setSiteName("testName");
        site.setLogin("test@ex.com");
        site.setPassword("123t");
        siteRepository.save(site);
        var link1 = new Link();
        link1.setUrl("https://www.example1.com");
        link1.setConvertedUrl("test11");
        link1.setSite(site);
        var link2 = new Link();
        link2.setUrl("https://www.example2.com");
        link2.setConvertedUrl("test12");
        link2.setSite(site);
        linkRepository.save(link1);
        linkRepository.save(link2);
        var statistic1 = new Statistic();
        statistic1.setLink(link1);
        statistic1.setCallCount(5L);
        var statistic2 = new Statistic();
        statistic2.setLink(link2);
        statistic2.setCallCount(15L);
        statisticRepository.save(statistic1);
        statisticRepository.save(statistic2);
        var statisticForLink1 = statisticService.getStatisticForLink(link1.getId());
        var statisticForLink2 = statisticService.getStatisticForLink(link2.getId());
        var total = statisticForLink1 + statisticForLink2;
        var statisticForSite = statisticService.getStatisticForSite(site.getId());
        assertThat(statisticForSite).isEqualTo(total);
        assertThat(statisticForLink1).isEqualTo(5L);
        assertThat(statisticForLink2).isEqualTo(15L);
        assertThat(statisticForSite).isEqualTo(20L);
    }
}