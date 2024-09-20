package ru.job4j.url.shortcut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.url.shortcut.model.Site;

import java.util.Optional;

public interface SiteRepository extends JpaRepository<Site, Long> {

    Boolean existsByLogin(String login);

    Optional<Site> findByLogin(String login);

    Optional<Site> findBySiteName(String siteName);

    @Transactional
    @Modifying
    @Query("""
        update Site s
        set s.login = :#{#site.login},
        s.password = :#{#site.password},
        s.siteName = :#{site.siteName}
        where s.id = :#{#site.id}
        """)
    int update(@Param("site") Site site);

    @Query("""
            select site from Site as site
            where site.login = :login and site.password = :password
            """)
    Optional<Site> findByLoginAndPassword(@Param("login") String login, @Param("password") String password);
}
