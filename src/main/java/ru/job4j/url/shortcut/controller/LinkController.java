package ru.job4j.url.shortcut.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.url.shortcut.dto.LinkDto;
import ru.job4j.url.shortcut.service.LinkService;
import ru.job4j.url.shortcut.service.StatisticService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/link")
public class LinkController {

    private LinkService linkService;

    private StatisticService statisticService;

    @PostMapping
    @PreAuthorize("#link.site.login == authentication.principal.login")
    public ResponseEntity<LinkDto> save(@RequestBody LinkDto linkDto) {
        var link = linkService.create(linkDto);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(link.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(linkDto);
    }

    @PostMapping("/convert")
    @PreAuthorize("#link.site.login == authentication.principal.login")
    public ResponseEntity<?> convertUrl(@RequestBody LinkDto linkDto) {
        return ResponseEntity.ok(linkService.convertUrl(linkDto.getSite(), linkDto.getUrl()));
    }

    @GetMapping("/original/{shortUrl}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getOriginal(@PathVariable String shortUrl) {
        var originalUrl = linkService.getOriginalUrl(shortUrl);
        if (!originalUrl.isEmpty()) {
            return ResponseEntity.ok(originalUrl);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{linkId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeById(@PathVariable Long linkId) {
        linkService.deleteById(linkId);
        if (linkService.findById(linkId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/statistic")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getLinkStatistic(@PathVariable Long linkId) {
        return ResponseEntity.ok(statisticService.getStatisticForLink(linkId));
    }
}
