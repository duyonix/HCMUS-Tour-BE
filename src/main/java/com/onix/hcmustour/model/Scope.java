package com.onix.hcmustour.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @Column(name = "logo", columnDefinition = "TEXT")
    private String logo;

    @ElementCollection
    @CollectionTable(name = "background", joinColumns = @JoinColumn(name = "scope_id"))
    @Column(name = "background")
    private List<String> backgrounds;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "x_2d")),
            @AttributeOverride(name = "y", column = @Column(name = "y_2d"))
    })
    private Coordinate2D coordinate2D;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "x_3d")),
            @AttributeOverride(name = "y", column = @Column(name = "y_3d")),
            @AttributeOverride(name = "z", column = @Column(name = "z_3d"))
    })
    private Coordinate3D coordinate3D;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("scopes")
    private Category category;

    @OneToMany(mappedBy = "scope", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Costume> costumes;
}
