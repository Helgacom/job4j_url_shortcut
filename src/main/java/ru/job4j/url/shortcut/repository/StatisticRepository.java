package ru.job4j.url.shortcut.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.url.shortcut.model.Statistic;

public interface StatisticRepository extends JpaRepository<Statistic, Long> {

    Statistic findByLinkId(Long id);

    @Transactional
    @Modifying
    @Query("update Statistic s set s.callCount = s.callCount + 1 where s.id = :id")
    void incrementCallCount(@Param("id") Long id);
}
