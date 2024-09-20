package ru.job4j.url.shortcut.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "links")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@ToString
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String url;

    private String convertedUrl;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private Site site;
}
