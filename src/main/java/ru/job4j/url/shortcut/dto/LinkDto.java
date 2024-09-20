package ru.job4j.url.shortcut.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.job4j.url.shortcut.model.Site;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LinkDto {

    private String url;

    private Site site;
}
