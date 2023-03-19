package com.onix.hcmustour.dto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.onix.hcmustour.model.Category;
import com.onix.hcmustour.model.Coordinate2D;
import com.onix.hcmustour.model.Coordinate3D;
import com.onix.hcmustour.model.Costume;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScopeDto {
    private Integer id;

    private String name;

    private String description;

    private String logo;

    private List<String> backgrounds;

    private Integer categoryId;

    private CategoryDto category;

    private Coordinate2D coordinate2D;

    private Coordinate3D coordinate3D;

    private List<CostumeDto> costumes;
}
