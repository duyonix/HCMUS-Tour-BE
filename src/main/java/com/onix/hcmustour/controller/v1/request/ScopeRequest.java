package com.onix.hcmustour.controller.v1.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onix.hcmustour.model.Coordinate2D;
import com.onix.hcmustour.model.Coordinate3D;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class ScopeRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotBlank(message = "Logo is required")
    private String logo;

    @NotEmpty(message = "Backgrounds is required")
    private List<String> backgrounds;

    @NotNull(message = "Category ID is required")
    private Integer categoryId;

    @NotNull(message = "Coordinate2D is required")
    private Coordinate2D coordinate2D;

    @NotNull(message = "Coordinate3D is required")
    private Coordinate3D coordinate3D;
}
