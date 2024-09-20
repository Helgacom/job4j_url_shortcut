package ru.job4j.url.shortcut.service;

import ru.job4j.url.shortcut.model.Statistic;

import java.util.List;
import java.util.Optional;

public interface StatisticService {

    Statistic save(Statistic statistic);

    Optional<Statistic> findById(Long id);

    void deleteById(Long id);

    List<Statistic> findAll();

    Long getStatisticForLink(Long id);

    Long getStatisticForSite(Long id);
}
