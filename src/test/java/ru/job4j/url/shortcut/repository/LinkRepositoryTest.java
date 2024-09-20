package ru.job4j.url.shortcut.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.url.shortcut.model.Site;
import ru.job4j.url.shortcut.model.Link;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LinkRepositoryTest {

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private LinkRepository linkRepository;

    @BeforeEach
    public void setUp() {
        linkRepository.deleteAll();
        siteRepository.deleteAll();
    }

    @AfterAll
    public void clearAll() {
        linkRepository.deleteAll();
        siteRepository.deleteAll();
    }

    @Test
    public void whenSaveLinkThenFindById() {
        var site = new Site();
        site.setSiteName("testName");
        site.setLogin("test@ex.com");
        site.setPassword("123t");
        siteRepository.save(site);
        var link = new Link();
        link.setUrl("https://www.example.com");
        link.setConvertedUrl("test11");
        link.setSite(site);
        linkRepository.save(link);
        var foundLink = linkRepository.findById(link.getId());
        assertThat(foundLink).isPresent();
        assertThat(foundLink.get().getUrl()).isEqualTo("https://www.example.com");
    }

    @Test
    public void whenFindAllThenReturnAllSites() {
        var site1 = new Site();
        site1.setSiteName("testName1");
        site1.setLogin("test1@ex.com");
        site1.setPassword("123t1");
        var site2 = new Site();
        site2.setSiteName("testName2");
        site2.setLogin("test2@ex.com");
        site2.setPassword("123t2");
        siteRepository.save(site1);
        siteRepository.save(site2);
        var link1 = new Link();
        link1.setUrl("https://www.example1.com");
        link1.setConvertedUrl("test11");
        link1.setSite(site1);
        var link2 = new Link();
        link2.setUrl("https://www.example2.com");
        link2.setConvertedUrl("test12");
        link2.setSite(site2);
        linkRepository.save(link1);
        linkRepository.save(link2);
        var links = linkRepository.findAll();
        assertThat(links).hasSize(2);
        assertThat(links).containsExactlyInAnyOrder(link1, link2);
        assertThat(links).extracting(Link::getSite).contains(site1, site2);
    }

    @Test
    public void whenDeleteLinkThenLinkNotFound() {
        var site = new Site();
        site.setSiteName("testName");
        site.setLogin("test@ex.com");
        site.setPassword("123t");
        siteRepository.save(site);
        var link = new Link();
        link.setUrl("https://www.example.com");
        link.setConvertedUrl("test11");
        link.setSite(site);
        linkRepository.save(link);
        linkRepository.deleteById(link.getId());
        var foundLink = linkRepository.findById(link.getId());
        assertThat(foundLink).isNotPresent();
        assertThat(linkRepository.findAll()).doesNotContain(link);
    }

    @Test
    public void whenFindLinksBySiteIdThenFoundSetLinks() {
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
        var links = linkRepository.findLinksBySiteId(site.getId());
        assertThat(links).isInstanceOf(HashSet.class);
        assertThat(links).hasSize(2);
        assertThat(links).containsExactlyInAnyOrder(link1, link2);
    }

    @Test
    public void whenFindByUrlThenFoundLink() {
        var site = new Site();
        site.setSiteName("testName");
        site.setLogin("test@ex.com");
        site.setPassword("123t");
        siteRepository.save(site);
        var link = new Link();
        link.setUrl("https://www.example.com");
        link.setConvertedUrl("test11");
        link.setSite(site);
        linkRepository.save(link);
        var foundLink = linkRepository.findByUrl("https://www.example.com");
        assertThat(foundLink).isPresent();
        assertThat(foundLink.get().getSite()).isEqualTo(site);
    }

    @Test
    public void whenFindByShortUrlThenFoundLink() {
        var site = new Site();
        site.setSiteName("testName");
        site.setLogin("test@ex.com");
        site.setPassword("123t");
        siteRepository.save(site);
        var link = new Link();
        link.setUrl("https://www.example.com");
        link.setConvertedUrl("test11");
        link.setSite(site);
        linkRepository.save(link);
        var foundLink = linkRepository.findByConvertedUrl("test11");
        assertThat(foundLink).isPresent();
        assertThat(foundLink.get().getSite()).isEqualTo(site);
    }
}