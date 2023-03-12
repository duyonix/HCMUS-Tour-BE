package com.onix.hcmustour.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Entity(name = "Scope")
@Table(name = "scope")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Scope {
    @Id
    @SequenceGenerator(name = "scope_sequence", sequenceName = "scope_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scope_sequence")
    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    @CollectionTable(name = "background", joinColumns = @JoinColumn(name = "scope_id"))
    @Column(name = "background")
    private List<String> backgrounds;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "scope", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Costume> costumes;
}
