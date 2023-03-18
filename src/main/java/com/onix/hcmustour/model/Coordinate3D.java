package com.onix.hcmustour.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Coordinate3D {
    @Column(name = "x_3d")
    private Double x;

    @Column(name = "y_3d")
    private Double y;

    @Column(name = "z_3d")
    private Double z;
}
