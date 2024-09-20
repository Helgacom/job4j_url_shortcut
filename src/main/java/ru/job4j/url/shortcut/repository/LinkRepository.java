package ru.job4j.url.shortcut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.url.shortcut.model.Link;

import java.util.Optional;
import java.util.Set;

public interface LinkRepository extends JpaRepository<Link, Long> {

    Set<Link> findLinksBySiteId(Long id);

    Optional<Link> findByUrl(String url);

    Optional<Link> findByConvertedUrl(String convertedUrl);

    @Transactional
    @Modifying
    @Query("""
        update Link l
        set l.url = :#{#link.url},
        l.convertedUrl = :#{#link.convertedUrl},
        l.site = :#{link.site}
        where l.id = :#{#link.id}
        """)
    int update(@Param("link") Link link);
}
