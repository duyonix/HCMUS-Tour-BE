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
public class Coordinate2D {
    @Column(name = "x_2d")
    private Double x;

    @Column(name = "y_2d")
    private Double y;
}
