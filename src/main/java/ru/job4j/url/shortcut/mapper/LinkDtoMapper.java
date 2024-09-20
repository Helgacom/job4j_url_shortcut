package ru.job4j.url.shortcut.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.job4j.url.shortcut.dto.LinkDto;
import ru.job4j.url.shortcut.model.Link;

@Mapper(componentModel = "spring")
@Component
public interface LinkDtoMapper {

    LinkDto linkToLinkDto(Link link);

    Link linkDtoToLink(LinkDto linkDto);
}
