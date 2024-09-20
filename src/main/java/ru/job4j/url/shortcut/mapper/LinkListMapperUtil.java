package ru.job4j.url.shortcut.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.job4j.url.shortcut.model.Link;
import ru.job4j.url.shortcut.repository.LinkRepository;

import java.util.Set;

@Named("LinkListMapperUtil")
@Component
@RequiredArgsConstructor
public class LinkListMapperUtil {

    private final LinkRepository linkRepository;

    @Named("findLinksBySiteId")
    public Set<Link> findLinksBySiteId(Long id) {
        return linkRepository.findLinksBySiteId(id);
    }
}
