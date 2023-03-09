package com.onix.hcmustour.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity(name = "Costume")
@Table(name = "costume")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Costume {
    @Id
    @SequenceGenerator(name = "costume_sequence", sequenceName = "costume_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "costume_sequence")
    @Column(name = "id", updatable = false)
    private String id;

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "model", nullable = false, columnDefinition = "TEXT")
    private String model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scope_id")
    private Scope scope;
}
