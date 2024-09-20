package ru.job4j.url.shortcut.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StatisticRepositoryTest {

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private LinkRepository linkRepository;

    @BeforeEach
    public void setUp() {
        statisticRepository.deleteAll();
        linkRepository.deleteAll();
        siteRepository.deleteAll();
    }

    @AfterAll
    public void clearAll() {
        statisticRepository.deleteAll();
        linkRepository.deleteAll();
        siteRepository.deleteAll();
    }


}