package ru.job4j.url.shortcut.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.url.shortcut.model.Site;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SiteRepositoryTest {

    @Autowired
    private SiteRepository siteRepository;

    @BeforeEach
    public void setUp() {
        siteRepository.deleteAll();
    }

    @AfterAll
    public void clearAll() {
        siteRepository.deleteAll();
    }

    @Test
    public void whenSaveSiteThenFindById() {
        var site = new Site();
        site.setSiteName("testName");
        site.setLogin("test@ex.com");
        site.setPassword("123t");
        siteRepository.save(site);
        var foundSite = siteRepository.findById(site.getId());
        assertThat(foundSite).isPresent();
        assertThat(foundSite.get().getSiteName()).isEqualTo("testName");
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
        var sites = siteRepository.findAll();
        assertThat(sites).hasSize(2);
        assertThat(sites).containsExactlyInAnyOrder(site1, site2);
        assertThat(sites).extracting(Site::getSiteName).contains("testName1", "testName2");
    }

    @Test
    public void whenDeleteSiteThenSiteNotFound() {
        var site = new Site();
        site.setSiteName("testName");
        site.setLogin("test@ex.com");
        site.setPassword("123t");
        siteRepository.save(site);
        siteRepository.deleteById(site.getId());
        var foundUser = siteRepository.findById(site.getId());
        assertThat(foundUser).isNotPresent();
        assertThat(siteRepository.findAll()).doesNotContain(site);
    }

    @Test
    public void whenFindByLoginAndPasswordThenFindSite() {
        var site = new Site();
        site.setSiteName("testName");
        site.setLogin("test@ex.com");
        site.setPassword("123t");
        siteRepository.save(site);
        var foundSite = siteRepository.findByLoginAndPassword("test@ex.com", "123t");
        assertThat(foundSite).isPresent();
        assertThat(foundSite.get().getSiteName()).isEqualTo("testName");
    }

    @Test
    public void whenFindByLoginThenFindSite() {
        var site = new Site();
        site.setSiteName("testName");
        site.setLogin("test@ex.com");
        site.setPassword("123t");
        siteRepository.save(site);
        var foundSite = siteRepository.findByLogin("test@ex.com");
        assertThat(foundSite).isPresent();
        assertThat(foundSite.get().getSiteName()).isEqualTo("testName");
    }

    @Test
    public void whenFindBySiteNameThenFindSite() {
        var site = new Site();
        site.setSiteName("testName");
        site.setLogin("test@ex.com");
        site.setPassword("123t");
        siteRepository.save(site);
        var foundSite = siteRepository.findBySiteName("testName");
        assertThat(foundSite).isPresent();
        assertThat(foundSite.get().getLogin()).isEqualTo("test@ex.com");
    }
}