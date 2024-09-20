package ru.job4j.url.shortcut.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "statistic")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@ToString
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private Long callCount;

    @ManyToOne
    @JoinColumn(name = "link_id")
    private Link link;
}
