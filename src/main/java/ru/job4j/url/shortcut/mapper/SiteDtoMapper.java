package ru.job4j.url.shortcut.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.job4j.url.shortcut.model.Site;
import ru.job4j.url.shortcut.dto.SiteDto;

@Mapper(componentModel = "spring",
        uses = LinkListMapperUtil.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
@Component
public interface SiteDtoMapper {

    @Mapping(target = "links", qualifiedByName = {"LinkListMapperUtil", "findLinksBySiteId"}, source = "site.id")
    SiteDto siteToSiteDto(Site site);

    Site siteDtoToSite(SiteDto siteDto);
}
