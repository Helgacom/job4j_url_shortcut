package ru.job4j.url.shortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.url.shortcut.model.Link;
import ru.job4j.url.shortcut.model.Statistic;
import ru.job4j.url.shortcut.repository.LinkRepository;
import ru.job4j.url.shortcut.repository.StatisticRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleStatisticService implements StatisticService {

    private final StatisticRepository statisticRepository;
    private final LinkRepository linkRepository;

    @Override
    public Statistic save(Statistic statistic) {
        return statisticRepository.save(statistic);
    }

    @Override
    public Optional<Statistic> findById(Long id) {
        return statisticRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        statisticRepository.deleteById(id);
    }

    @Override
    public List<Statistic> findAll() {
        return statisticRepository.findAll();
    }

    @Override
    public Long getStatisticForLink(Long id) {
        return statisticRepository.findByLinkId(id).getCallCount();
    }

    @Override
    public Long getStatisticForSite(Long id) {
        var links = linkRepository.findLinksBySiteId(id);
        var rsl = 0L;
        for (Link link : links) {
            rsl += getStatisticForLink(link.getId());
        }
        return rsl;
    }
}
