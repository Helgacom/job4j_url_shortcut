package ru.job4j.url.shortcut.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.url.shortcut.model.Link;
import ru.job4j.url.shortcut.service.LinkService;
import ru.job4j.url.shortcut.service.StatisticService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/links")
public class LinksController {

    private LinkService linkService;

    private StatisticService statisticService;

    @GetMapping("/{siteId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Link>> getAllSiteLinks(@PathVariable("siteId") Long siteId) {
        var rsl = linkService.findSiteLinks(siteId);
        if (!rsl.isEmpty()) {
            return ResponseEntity.ok(rsl);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/statistic")
    public ResponseEntity<?> getSiteStatistic(@PathVariable Long siteId) {
        return ResponseEntity.ok(statisticService.getStatisticForSite(siteId));
    }
}
