package ru.job4j.url.shortcut.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.url.shortcut.dto.SiteDto;
import ru.job4j.url.shortcut.security.sitedetails.SiteDetailsImpl;
import ru.job4j.url.shortcut.service.SiteService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/site")
public class SiteController {

    private final SiteService siteService;

    @PostMapping
    @PostAuthorize("#site.login == authentication.principal.login")
    public ResponseEntity<SiteDto> save(@RequestBody SiteDto siteDto) {
        var site = siteService.create(siteDto);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(site.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(siteDto);
    }

    @PutMapping
    @PreAuthorize("site.login == authentication.principal.login")
    public ResponseEntity<Void> update(@RequestBody SiteDto siteDto) {
        if (siteService.update(siteDto)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PatchMapping
    @PreAuthorize("#site.login == authentication.principal.login")
    public ResponseEntity<Void> change(@RequestBody SiteDto siteDto) {
        if (siteService.update(siteDto)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{siteId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeById(@PathVariable Long siteId) {
        siteService.deleteById(siteId);
        if (siteService.findById(siteId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/current")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SiteDetailsImpl> getCurrentSiteDetails(@AuthenticationPrincipal SiteDetailsImpl siteDetails) {
        return ResponseEntity.ok(siteDetails);
    }
}
